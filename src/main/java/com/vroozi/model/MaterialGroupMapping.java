package com.vroozi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "material_group_mapping_flat_data")
public class MaterialGroupMapping {

  @Id
  private String id;
  private int unitId;
  private String catalogCategoryCode;
  private String companyCategoryCode;
  private String companyLabel;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getUnitId() {
    return unitId;
  }

  public void setUnitId(int unitId) {
    this.unitId = unitId;
  }

  public String getCatalogCategoryCode() {
    return catalogCategoryCode;
  }

  public void setCatalogCategoryCode(String catalogCategoryCode) {
    this.catalogCategoryCode = catalogCategoryCode;
  }

  public String getCompanyCategoryCode() {
    return companyCategoryCode;
  }

  public void setCompanyCategoryCode(String companyCategoryCode) {
    this.companyCategoryCode = companyCategoryCode;
  }

  public String getCompanyLabel() {
    return companyLabel;
  }

  public void setCompanyLabel(String companyLabel) {
    this.companyLabel = companyLabel;
  }
}