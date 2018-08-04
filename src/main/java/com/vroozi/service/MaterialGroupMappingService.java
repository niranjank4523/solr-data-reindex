package com.vroozi.service;

import com.vroozi.model.MaterialGroupMapping;

import java.util.List;

/**
 * @author Muhammad Haris
 * @date 04-Aug-18
 */
public interface MaterialGroupMappingService {

  /**
   * Finds material groups mappings against given unitId and then set levels info as need for
   * reindexing solr data
   *
   * @param unitId
   * @return
   */
  List<MaterialGroupMapping> getMaterialGroupMappingForReindexing(Integer unitId);
}
