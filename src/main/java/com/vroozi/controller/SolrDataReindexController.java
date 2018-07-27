package com.vroozi.controller;

import com.vroozi.model.SolrReindexProcess;
import com.vroozi.service.SolrDataReindexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolrDataReindexController {

  @Autowired
  private SolrDataReindexService solrDataReindexService;

  @RequestMapping(value = "/reindex")
  public SolrReindexProcess reindex(@RequestParam(required = false) Integer unitId)
      throws Exception {
    return solrDataReindexService.submitSolrReindexingProcess(unitId);
  }
}