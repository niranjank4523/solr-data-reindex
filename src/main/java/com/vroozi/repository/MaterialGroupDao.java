package com.vroozi.repository;

import com.vroozi.model.MaterialGroupMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialGroupDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<MaterialGroupMapping> findByUnitId(Integer unitId) {
    Query query = new Query(Criteria.where("unitId").is(unitId));
    return mongoTemplate.find(query, MaterialGroupMapping.class);
  }

}