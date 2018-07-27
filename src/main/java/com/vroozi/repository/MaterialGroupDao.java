package com.vroozi.repository;

import com.mongodb.BasicDBObject;
import com.vroozi.model.MaterialGroupMapping;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialGroupDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  public Set<Integer> findAllUnitIds() {
    TypedAggregation<MaterialGroupMapping> aggregation = Aggregation.newAggregation(
        MaterialGroupMapping.class, Aggregation.group("unitId").push(
            new BasicDBObject("_id", "$_id")).as("materialGroupMappings"));

    AggregationResults<MaterialGroupMapping> results = mongoTemplate.
        aggregate(aggregation, MaterialGroupMapping.class);

    List<MaterialGroupMapping> materialGroupMappingList = results.getMappedResults();
    return materialGroupMappingList.stream()
        .map(materialGroupMapping -> Integer.parseInt(materialGroupMapping.getId()))
        .collect(Collectors.toSet());
  }

  public List<MaterialGroupMapping> findByUnitIdOrderByCatalogCategoryCodeDesc(Integer unitId) {
    Query query = new Query(Criteria.where("unitId").is(unitId));
    return mongoTemplate.find(query, MaterialGroupMapping.class);
  }

}