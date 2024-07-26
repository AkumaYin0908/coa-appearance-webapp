package com.coa.constants.query_contants;

public interface AddressConstant {


    String FIND_BY_BARANGAY = "select * from address where barangay = ? ";

    String FIND_BY_MUNICIPALITY = "select * from address where municipality = ?";

    String FIND_BY_PROVINCE = "select * from address where province = ?";

    String FIND_BY_REGION = "select * from address where region = ?";

    String FIND_BY_CODES = "select * from address where barangay = ? and municipality = ? " +
            "and province = ? and region = ?";


}
