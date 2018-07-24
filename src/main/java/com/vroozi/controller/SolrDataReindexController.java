package com.vroozi.controller;

import com.vroozi.model.Result;
import com.vroozi.service.SolrDataReindexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolrDataReindexController {

  @Autowired
  private SolrDataReindexService solrDataReindexService;

  @RequestMapping(value = "/reindex/organization/{unitId}")
  public Result reindex(@PathVariable int unitId) throws Exception {
    return solrDataReindexService.reindex(unitId);
  }
}