package com.vroozi.controller;

import com.vroozi.model.SolrReindexProcess;
import com.vroozi.service.SolrDataReindexService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(value = "/retry", method = RequestMethod.POST)
  public String retryFailedBatches(@RequestBody List<Integer> unitIds) {
    return solrDataReindexService.submitRetryFailedBatchExecution(unitIds);
  }
}