package com.vroozi.model;

public class FailedRecord {
  private int start;
  private int recordsPerPage;
  private String stackTrace;

  public FailedRecord(int start, int recordsPerPage, String stackTrace) {
    this.start = start;
    this.recordsPerPage = recordsPerPage;
    this.stackTrace = stackTrace;
  }

  public int getRecordsPerPage() {
    return recordsPerPage;
  }

  public void setRecordsPerPage(int recordsPerPage) {
    this.recordsPerPage = recordsPerPage;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }
}