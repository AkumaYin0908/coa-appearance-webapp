package com.coa.constants.query_contants;

public interface PurposeConstant {

    String findByDescription = "select * from purpose where description = ?";

    String findDescriptions = "select id, description from purpose";

}
