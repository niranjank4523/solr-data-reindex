package com.example.demo.repository;

import com.example.demo.model.MaterialGroupMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialGroupRepository extends MongoRepository<MaterialGroupMapping, String> {
  List<MaterialGroupMapping> findByUnitIdOrderByCatalogCategoryCodeDesc(int unitId);
}