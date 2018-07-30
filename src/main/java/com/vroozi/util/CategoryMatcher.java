/**
 *
 */
package com.vroozi.util;

import com.vroozi.model.MaterialGroupMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Muhammad Haris
 */
public class CategoryMatcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryMatcher.class);
  private static final int UNSPSC_CODE_LENGTH = 8;

  private CategoryMatcher() {
  }

  public static MaterialGroupMapping findMaterialGroupMappingAgainstItemMatGroup(
    List<MaterialGroupMapping> materialGroupMappings, String itemMatGroup) {
    try {
      LOGGER.trace("Finding MaterialGroupMapping against itemMatGroup: {}", itemMatGroup);
      for (MaterialGroupMapping materialGroupMapping : materialGroupMappings) {

        String[] catalogCategoryCodes = materialGroupMapping.getCatalogCategoryCode().split(",");
        String companyCategoryCode = materialGroupMapping.getCompanyCategoryCode();

        /* if any of the material group mapping matched with itemMatGroup */
        if (Arrays.stream(catalogCategoryCodes).anyMatch(
          catalogCategoryCode -> categoryCodeMatchedWithMatGroup(companyCategoryCode,
            catalogCategoryCode.trim(),
            itemMatGroup))) {
          return materialGroupMapping;
        }
      }
    } catch (Exception e) {
      LOGGER.error(
        "Exception while finding matching MaterialGroupMapping against given itemMatGroup: {}",
        itemMatGroup, e);
    }

    return null;
  }

  private static boolean categoryCodeMatchedWithMatGroup(String companyCategoryCode,
                                                         String catalogCategoryCode,
                                                         String matGroup) {
    if (catalogCategoryCode.contains("-")) {
      Long[] categoryCodeRange = getCategoryCodeRange(catalogCategoryCode);
      return matGroupWithinCodeRange(categoryCodeRange, Long.parseLong(matGroup));
    } else if ((catalogCategoryCode.endsWith("*") && matGroupMatchedWithPattern(catalogCategoryCode,
      matGroup))
      || matGroupExactlyMatched(companyCategoryCode, catalogCategoryCode, matGroup)) {
      return true;
    }

    return false;
  }

  private static boolean matGroupWithinCodeRange(Long[] categoryCodeRange, Long matGroup) {
    return matGroup >= categoryCodeRange[0] && matGroup <= categoryCodeRange[1];
  }

  private static boolean matGroupMatchedWithPattern(String catalogCategoryCode, String matGroup) {
    String regex = catalogCategoryCode.substring(0, catalogCategoryCode.length() - 1) + ".*";
    return Pattern.matches(regex, matGroup);
  }

  private static boolean matGroupExactlyMatched(String catalogCategoryCode,
                                                String companyCategoryCode, String matGroup) {
    return catalogCategoryCode.equals(matGroup) || companyCategoryCode.equals(matGroup);
  }

  private static Long[] getCategoryCodeRange(String catalogCategoryCode) {
    int indexHyphen = catalogCategoryCode.indexOf('-');
    String unspscRangeStart = catalogCategoryCode.substring(0, indexHyphen);
    String unspscRangeEnd =
      catalogCategoryCode.substring(indexHyphen + 1, catalogCategoryCode.length());
    unspscRangeStart = createRangeEnds(unspscRangeStart, "0");
    unspscRangeEnd = createRangeEnds(unspscRangeEnd, "9");

    return new Long[]{Long.parseLong(unspscRangeStart), Long.parseLong(unspscRangeEnd)};
  }

  public static String createRangeEnds(String input, String toAppend) {
    if (input.contains("*")) {
      int indexAsterisk = input.indexOf('*');
      int totalCharactersAfterAsterisk = UNSPSC_CODE_LENGTH - indexAsterisk;
      StringBuilder builder = new StringBuilder(input.substring(0, indexAsterisk));

      for (int i = 0; i < totalCharactersAfterAsterisk; i++) {
        builder.append(toAppend);
      }
      return builder.toString();
    } else {
      return input;
    }
  }
}
