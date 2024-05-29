package com.coa.constants.query_contants;

public interface PositionConstant {

    String findByTitle = "select * from position where name = ?";

    String findTitles = "select title, name from position";
}
