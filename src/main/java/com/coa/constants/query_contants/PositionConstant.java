package com.coa.constants.query_contants;

public interface PositionConstant {

    String FIND_BY_TITLE = "select * from position where title = ?";

    String FIND_TITLES = "select id, title from position";
}
