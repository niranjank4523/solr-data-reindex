package com.vroozi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "solr_reindex_process")
public class SolrReindexProcess {

  @Id
  private String processId;
  private Integer unitId;
  private Date startTime;
  private Date endTime;
  private Date createdDate = new Date();
  private Date lastUpdated;
  private ProcessState processState = ProcessState.UNPROCESSED;
  private ProcessType processType = ProcessType.REINDEX;
  private List<FailedRecord> failedRecords = new ArrayList<>();
  private List<Result> results = new ArrayList<>();

  public SolrReindexProcess(Integer unitId) {
    this.unitId = unitId;
  }

  public String getProcessId() {
    return processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }

  public Integer getUnitId() {
    return unitId;
  }

  public void setUnitId(Integer unitId) {
    this.unitId = unitId;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public ProcessState getProcessState() {
    return processState;
  }

  public void setProcessState(ProcessState processState) {
    this.processState = processState;
  }

  public ProcessType getProcessType() {
    return processType;
  }

  public void setProcessType(ProcessType processType) {
    this.processType = processType;
  }

  public List<FailedRecord> getFailedRecords() {
    return failedRecords;
  }

  public void setFailedRecords(List<FailedRecord> failedRecords) {
    this.failedRecords = failedRecords;
  }

  public List<Result> getResults() {
    return results;
  }

  public void setResults(List<Result> results) {
    this.results = results;
  }
}