package com.coa.constants.query_contants;

public interface PurposeConstant {

    String FIND_BY_DESCRIPTION = "select * from purpose where description = ?";

    String FIND_DESCRIPTIONS = "select id, description from purpose";

}
