package com.coa.constants.query_contants;

public interface AppearanceConstant {

    String FIND_APPEARANCE_ORDER_BY_DATE_ISSUED_DESC = "select * from appearance order by date_issued desc";

    String FIND_BY_VISITOR = "select * from appearance where visitor = ?";

    String FIND_VISITOR_BY_NAME = "select * from appearance  where visitor.name = ?";
    String FIND_BY_VISITOR_AND_DATE_ISSUED = "select * from appearance where visitor = ? and date_issued = ?";

    String FIND_BY_VISITOR_AND_DATE_FROM = "select * from appearance where visitor = ? and date_from = ?";

    String FIND_BY_PURPOSE = "select * from appearance join purpose on appearance.purpose = purpose.id where purpose.description = ?";

    String FIND_BY_DATE_ISSUED = "select * from appearance where date_issued = ?";

    String FIND_BY_MONTH = "select * from appearance where MONTH(date_issued) = ?";

    String FIND_BY_YEAR = "select * from appearance where YEAR(date_issued) = ?";

    String FIND_BY_REFERENCE = "select * from appearance where reference = ?";





}
