package com.example.demo.controller;

import com.example.demo.model.Result;
import com.example.demo.service.SolrDataReindexService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SolrDataReindexController {

  @Autowired
  private SolrDataReindexService solrDataReindexService;

  @RequestMapping(value = "/reindex/organization/{unitId}")
  public Result reindex(@PathVariable int unitId) throws IOException, SolrServerException {
    return solrDataReindexService.reindex(unitId);
  }
}