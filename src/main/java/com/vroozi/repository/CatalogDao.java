package com.vroozi.repository;

import com.mongodb.BasicDBObject;
import com.vroozi.model.Catalog;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  public Set<Integer> findDistinctUnitIdsFromCatalogs() {
    TypedAggregation<Catalog> aggregation = Aggregation.newAggregation(
        Catalog.class, Aggregation.group("unitId").push(
            new BasicDBObject("_id", "$_id")).as("catalogs"));

    AggregationResults<Catalog> results = mongoTemplate.
        aggregate(aggregation, Catalog.class);

    List<Catalog> catalogs = results.getMappedResults();
    return catalogs.stream()
        .map(catalog -> Integer.parseInt(catalog.getId()))
        .collect(Collectors.toSet());
  }

}