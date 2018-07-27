package com.vroozi.repository;

import com.vroozi.model.ProcessState;
import com.vroozi.model.ProcessType;
import com.vroozi.model.SolrReindexProcess;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolrReindexProcessRepository extends MongoRepository<SolrReindexProcess, String> {

  SolrReindexProcess findByProcessTypeAndProcessState(ProcessType processType,
      ProcessState processState);

  SolrReindexProcess findByProcessTypeAndProcessStateAndUnitId(ProcessType processType,
      ProcessState processState, int unitId);
}