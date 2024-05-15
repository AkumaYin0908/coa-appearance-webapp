package com.coa.constants.query_contants;

public interface AppearanceConstant {

    String findByVisitorId = "select * from appearance AS a where a.visitor.id = ?";

    String findByVisitorName = "select * from appearance  where appearance.visitor.name = ?";
    String findByVisitorAndDateIssued = "select * from appearance where visitor.id = ? and date_issued = ?";

    String findByPurpose = "select * from appearance where purpose = ?";

    String findByDateIssued = "select * from appearance where date_issued = ?";

    String findByMonth = "select * from appearance where MONTH(date_issued) = ?";

    String findByYear = "select * from appearance where MONTH(date_issued) = ? and YEAR(date_issued) = ?";





}
