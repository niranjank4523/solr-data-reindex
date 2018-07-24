package com.vroozi.repository;

import com.vroozi.model.MaterialGroupMapping;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialGroupRepository extends MongoRepository<MaterialGroupMapping, String> {

  List<MaterialGroupMapping> findByUnitIdOrderByCatalogCategoryCodeDesc(int unitId);
}