package com.vroozi.util;

/**
 * @author Muhammad Haris
 * @date 04-Aug-18
 */
public final class FieldNames {

  private FieldNames() {
  }

  public static class CommonFields {

    public static final String _ID = "_id";
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";
    public static final String UNIT_ID = "unitId";
    public static final String ACTIVE = "active";
    public static final String DELETED = "deleted";
    public static final String UPDATED_DATE = "updatedDate";
    public static final String USER_ID = "userId";

    private CommonFields() {
    }
  }

  public static class MaterialGroupMappingFields {

    public static final String COMPANY_CATEGORY_CODE = "companyCategoryCode";
    public static final String LEVEL1_VAL = "level1Val";
    public static final String LEVEL2_VAL = "level2Val";
    public static final String LEVEL3_VAL = "level3Val";
  }
}
