package com.example.demo.service;

import com.example.demo.model.MaterialGroupMapping;
import com.example.demo.model.Result;
import com.example.demo.repository.MaterialGroupRepository;
import com.example.demo.util.CategoryMatcher;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolrDataReindexService {

  @Autowired
  private MaterialGroupRepository materialGroupRepository;

  public Result reindex(int unitId) throws IOException, SolrServerException {
    String urlString = "http://localhost:8983/solr/a_common";
    HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
    solr.setParser(new XMLResponseParser());
    SolrQuery query = new SolrQuery();
    query.set("q", "unitid:" + unitId);
    QueryResponse response = solr.query(query);

    List<SolrDocument> docList = response.getResults();

    List<MaterialGroupMapping> materialGroupMappings = materialGroupRepository
      .findByUnitIdOrderByCatalogCategoryCodeDesc(unitId);

    boolean updated = false;
    String id;
    String matGroupCode;
    String matGroupLabel;
    String matGroupInfo;
    String vendorName;

    List<String> idsUpdated = new ArrayList<>();
    List<String> errors = new ArrayList<>();

    List<SolrInputDocument> solrInputDocuments = new ArrayList<>();

    for (SolrDocument doc : docList) {
      SolrInputDocument solrInputDocument = toSolrInputDocument(doc);
      id = solrInputDocument.get("id").getValue().toString();
      solrInputDocument.removeField("a_auto");
      solrInputDocument.removeField("a_autoPhrase");
      solrInputDocument.removeField("manufact_mat_auto");
      solrInputDocument.removeField("manufact_mat_autoPhrase");
      solrInputDocument.removeField("ext_quote_id_auto");
      solrInputDocument.removeField("ext_quote_id_autoPhrase");
      solrInputDocument.removeField("bundle_no_auto");
      solrInputDocument.removeField("bundle_no_autoPhrase");
      solrInputDocument.removeField("a_spell");
      solrInputDocument.removeField("a_spellPhrase");
      solrInputDocument.removeField("text");
      solrInputDocument.removeField("descSuggestions");
      solrInputDocument.removeField("customFieldsSearchableSuggestions");
      solrInputDocument.removeField("matGroupLabelSuggestions");
      solrInputDocument.removeField("vendorNameSuggestions");
      solrInputDocument.removeField("suggestions");
      solrInputDocument.removeField("suggestionsedge");
      matGroupCode = solrInputDocument.get("mat_group").getValue().toString();
      vendorName = solrInputDocument.get("vendor_name").getValue().toString();
      if (matGroupCode != null && !matGroupCode.isEmpty()) {
        MaterialGroupMapping materialGroupMapping = CategoryMatcher.findMaterialGroupMappingAgainstItemMatGroup(materialGroupMappings, matGroupCode);
        if (materialGroupMapping != null) {
          matGroupLabel = materialGroupMapping.getCompanyLabel();
          if (matGroupLabel == null) {
            errors.add("No matGroupLabel found for unitId: " + unitId + " id: " + id + " and matGroupCode: " + matGroupCode);
            continue;
          }
          matGroupInfo = materialGroupMapping.getCompanyCategoryCode() + "|||" + URLEncoder.encode(matGroupLabel, "UTF-8");
          solrInputDocument.removeField("mat_group_label");
          solrInputDocument.removeField("mat_group_info");
          solrInputDocument.addField("mat_group_label", matGroupLabel);
          solrInputDocument.addField("mat_group_info", matGroupInfo);
          updated = true;
        }
      }

      if (vendorName != null && !vendorName.isEmpty()) {
        solrInputDocument.removeField("vendor_name_lowercase");
        solrInputDocument.addField("vendor_name_lowercase", vendorName);
        updated = true;
      }

      if (updated) {
        idsUpdated.add(id);
        updated = false;
      }
      solrInputDocuments.add(solrInputDocument);
    }

    if (!solrInputDocuments.isEmpty()) {
      solr.add(solrInputDocuments);
      solr.commit();
    }
    return new Result(idsUpdated, errors);
  }


  private SolrInputDocument toSolrInputDocument(SolrDocument d) {
    SolrInputDocument doc = new SolrInputDocument();
    for (String name : d.getFieldNames()) {
      doc.addField(name, d.getFieldValue(name), 1.0f);
    }
    return doc;
  }
}