package com.vroozi.repository;

import com.vroozi.model.MaterialGroupMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vroozi.util.FieldNames.CommonFields.UNIT_ID;
import static com.vroozi.util.FieldNames.MaterialGroupMappingFields.COMPANY_CATEGORY_CODE;
import static com.vroozi.util.FieldNames.MaterialGroupMappingFields.LEVEL1_VAL;
import static com.vroozi.util.FieldNames.MaterialGroupMappingFields.LEVEL2_VAL;
import static com.vroozi.util.FieldNames.MaterialGroupMappingFields.LEVEL3_VAL;

@Repository
public class MaterialGroupDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  public MaterialGroupMapping findByUnitIdAndCompanyCategoryCode(Integer unitId,
      String companyCategoryCode) {
    final Query query = new Query(
        Criteria.where(UNIT_ID).is(unitId).and(COMPANY_CATEGORY_CODE).is(companyCategoryCode));
    return mongoTemplate.findOne(query, MaterialGroupMapping.class);
  }

  public List<MaterialGroupMapping> findByUnitId(Integer unitId) {
    Query query = new Query(Criteria.where(UNIT_ID).is(unitId));
    return mongoTemplate.find(query, MaterialGroupMapping.class);
  }

  public List<MaterialGroupMapping> findMappingsByUnitIdAtGivenLevel(Integer unitId, int level) {

    Query query = new Query(
        Criteria.where(UNIT_ID).is(unitId).and(getFieldAccordingToLevel(level)).exists(true));
    return mongoTemplate.find(query, MaterialGroupMapping.class);
  }

  private String getFieldAccordingToLevel(int level) {
    if (level == 3) {
      return LEVEL3_VAL;
    } else if (level == 2) {
      return LEVEL2_VAL;
    } else {
      return LEVEL1_VAL;
    }
  }

}