package com.coa.constants.query_contants;

public interface VisitorConstant {


    String findVisitorByName = "select * from visitor where name = ?";

    String findNames = "select id, name from visitor";


}
