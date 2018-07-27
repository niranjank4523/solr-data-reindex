package com.vroozi.model;

import java.util.ArrayList;
import java.util.List;

public class Result {

  Integer unitId;
  List<String> idsUpdated = new ArrayList<>();
  List<String> errors = new ArrayList<>();
  int totalUpdated;

  public Result(Integer unitId) {
    this.unitId = unitId;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public List<String> getIdsUpdated() {
    return idsUpdated;
  }

  public void setIdsUpdated(List<String> idsUpdated) {
    this.idsUpdated = idsUpdated;
  }

  public int getTotalUpdated() {
    return idsUpdated.size();
  }

  public void setTotalUpdated(int totalUpdated) {
    this.totalUpdated = totalUpdated;
  }
}