package com.coa.constants.query_contants;

public interface AppearanceConstant {

    String findByVisitor = "select * from appearance where visitor = ?";

    String findByVisitorName = "select * from appearance  where visitor.name = ?";
    String findByVisitorAndDateIssued = "select * from appearance where visitor = ? and date_issued = ?";

    String findByPurpose = "select * from appearance where description = ?";

    String findByDateIssued = "select * from appearance where date_issued = ?";

    String findByMonth = "select * from appearance where MONTH(date_issued) = ?";

    String findByYear = "select * from appearance where MONTH(date_issued) = ? and YEAR(date_issued) = ?";

    String findByReference = "select * from appearance where reference = ?";





}
