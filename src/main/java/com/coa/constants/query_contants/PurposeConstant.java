package com.coa.constants.query_contants;

public interface PurposeConstant {

    String findByDescription = "select * from purpose where purpose = ?";

    String findDescriptions = "select id, purpose from purpose";

}
