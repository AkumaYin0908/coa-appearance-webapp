package com.coa.constants.query_contants;

public interface PositionConstant {

    String findByTitle = "select * from position where title = ?";

    String findTitles = "select id, title from position";
}
