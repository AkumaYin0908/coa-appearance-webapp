package com.coa.constants.query_contants;

public interface VisitorConstant {


    String FIND_VISITOR_BY_NAME = "select * from visitor where name = ?";

    String FIND_NAMES = "select id, name from visitor";


}
