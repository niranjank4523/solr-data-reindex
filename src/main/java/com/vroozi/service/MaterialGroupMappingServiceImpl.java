package com.vroozi.service;

import com.vroozi.model.MaterialGroupMapping;
import com.vroozi.repository.MaterialGroupDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Muhammad Haris
 * @date 04-Aug-18
 */
@Service
public class MaterialGroupMappingServiceImpl implements MaterialGroupMappingService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(MaterialGroupMappingServiceImpl.class);

  @Autowired
  private MaterialGroupDao materialGroupDao;

  @Override
  public List<MaterialGroupMapping> getMaterialGroupMappingForReindexing(Integer unitId) {
    LOGGER.debug("Getting Material Groups for Reindexing for unitId: {}", unitId);
    List<MaterialGroupMapping> materialGroupMappings = new ArrayList<>();
    Collection<MaterialGroupMapping> materialGroupLevel1Mappings = null;
    Collection<MaterialGroupMapping> materialGroupLevel2Mappings = null;

    List<MaterialGroupMapping> materialGroupLevel3Mappings =
        materialGroupDao.findMappingsByUnitIdAtGivenLevel(unitId, 3);

    if (!CollectionUtils.isEmpty(materialGroupLevel3Mappings)) {
      setParentCategoriesInfo(unitId, 3, materialGroupLevel3Mappings, null);
    }

    materialGroupLevel2Mappings = materialGroupDao.findMappingsByUnitIdAtGivenLevel(unitId, 2);

    if (!CollectionUtils.isEmpty(materialGroupLevel2Mappings)) {
      setParentCategoriesInfo(unitId, 2, materialGroupLevel2Mappings,
          materialGroupLevel3Mappings);
    }

    materialGroupLevel1Mappings = materialGroupDao.findMappingsByUnitIdAtGivenLevel(unitId, 1);

    if (!CollectionUtils.isEmpty(materialGroupLevel1Mappings)) {
      for (MaterialGroupMapping materialGroupLevel1Mapping : materialGroupLevel1Mappings) {
        setMatGroupInfoLevelValue(1, materialGroupLevel1Mapping,
            materialGroupLevel1Mapping.getCompanyCategoryCode(),
            materialGroupLevel1Mapping.getCompanyLabel());
      }
    }

    if (!CollectionUtils.isEmpty(materialGroupLevel3Mappings)) {
      materialGroupMappings.addAll(materialGroupLevel3Mappings);
    }

    if (!CollectionUtils.isEmpty(materialGroupLevel2Mappings)) {
      materialGroupMappings.addAll(materialGroupLevel2Mappings);
    }

    if (!CollectionUtils.isEmpty(materialGroupLevel1Mappings)) {
      materialGroupMappings.addAll(materialGroupLevel1Mappings);
    }

    return materialGroupMappings;
  }

  private Collection<MaterialGroupMapping> setParentCategoriesInfo(Integer unitId, int level,
      Collection<MaterialGroupMapping> materialGroupMappings,
      List<MaterialGroupMapping> childMappings) {
    LOGGER.trace("Processing {} mappings at level {} for unitId: {}", materialGroupMappings.size(),
        level, unitId);
    Map<String, MaterialGroupMapping> parentCategoriesMap = new HashMap<>();

    for (MaterialGroupMapping materialGroupMapping : materialGroupMappings) {
      /* set code and label at its own level */
      setMatGroupInfoLevelValue(level, materialGroupMapping,
          materialGroupMapping.getCompanyCategoryCode(), materialGroupMapping.getCompanyLabel());

      String parentCategoryCode = materialGroupMapping.getParent();
      MaterialGroupMapping parentMapping = parentCategoriesMap.get(parentCategoryCode);

      if (parentMapping == null) {
        parentMapping =
            materialGroupDao.findByUnitIdAndCompanyCategoryCode(unitId, parentCategoryCode);
        parentCategoriesMap.put(parentCategoryCode, parentMapping);
      }

      /* prepend parent label into its own label */
      materialGroupMapping.setCompanyLabel(
          parentMapping.getCompanyLabel() + ", " + materialGroupMapping.getCompanyLabel());
      /* set code and label at parent level */
      setMatGroupInfoLevelValue(level - 1, materialGroupMapping,
          parentMapping.getCompanyCategoryCode(), parentMapping.getCompanyLabel());

      if (!CollectionUtils.isEmpty(childMappings)) {
        setGrandParentInfoInChildMappings(level, materialGroupMapping.getCompanyCategoryCode(),
            parentMapping, childMappings);
      }
    }

    return parentCategoriesMap.values();
  }

  private void setGrandParentInfoInChildMappings(int level, String parentCategoryCode,
      MaterialGroupMapping grandParentMapping, List<MaterialGroupMapping> materialGroupMappings) {

    for (MaterialGroupMapping materialGroupMapping : materialGroupMappings) {
      if (materialGroupMapping.getParent().equals(parentCategoryCode)) {
        materialGroupMapping.setCompanyLabel(
            grandParentMapping.getCompanyLabel() + ", " + materialGroupMapping.getCompanyLabel());
        setMatGroupInfoLevelValue(level - 1, materialGroupMapping,
            grandParentMapping.getCompanyCategoryCode(), grandParentMapping.getCompanyLabel());
      }
    }
  }

  private void setMatGroupInfoLevelValue(int level, MaterialGroupMapping materialGroupMapping,
      String code, String label) {
    String value = code + "|||" + label;
    if (level == 3) {
      materialGroupMapping.setMatGroupInfoLevel3(value);
    } else if (level == 2) {
      materialGroupMapping.setMatGroupInfoLevel2(value);
    } else {
      materialGroupMapping.setMatGroupInfoLevel1(value);
    }
  }
}
