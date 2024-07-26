package com.coa.constants.query_contants;

public interface VisitorConstant {


    String FIND_VISITOR_BY_NAME = "select * from visitor where first_name = ? and middle_initial = ? and last_name =?";

    String FIND_NAMES = "select id, name from visitor";


}
