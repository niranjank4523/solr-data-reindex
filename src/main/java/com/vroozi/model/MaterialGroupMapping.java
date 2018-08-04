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
  private String parent;
  private String level1Val;
  private String level2Val;
  private String level3Val;

  private String matGroupInfoLevel1;
  private String matGroupInfoLevel2;
  private String matGroupInfoLevel3;

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

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getLevel1Val() {
    return level1Val;
  }

  public void setLevel1Val(String level1Val) {
    this.level1Val = level1Val;
  }

  public String getLevel2Val() {
    return level2Val;
  }

  public void setLevel2Val(String level2Val) {
    this.level2Val = level2Val;
  }

  public String getLevel3Val() {
    return level3Val;
  }

  public void setLevel3Val(String level3Val) {
    this.level3Val = level3Val;
  }

  public String getMatGroupInfoLevel1() {
    return matGroupInfoLevel1;
  }

  public void setMatGroupInfoLevel1(String matGroupInfoLevel1) {
    this.matGroupInfoLevel1 = matGroupInfoLevel1;
  }

  public String getMatGroupInfoLevel2() {
    return matGroupInfoLevel2;
  }

  public void setMatGroupInfoLevel2(String matGroupInfoLevel2) {
    this.matGroupInfoLevel2 = matGroupInfoLevel2;
  }

  public String getMatGroupInfoLevel3() {
    return matGroupInfoLevel3;
  }

  public void setMatGroupInfoLevel3(String matGroupInfoLevel3) {
    this.matGroupInfoLevel3 = matGroupInfoLevel3;
  }
}