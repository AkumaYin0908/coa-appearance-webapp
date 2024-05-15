package com.coa.constants.query_contants;

public interface LeaderConstant {

    String findByName = "select * leader where name = ?";

    String getNames  = "select name from leader";

    String updateStatus = "update leader set in_charge = ?";

    String findByStatus = "select * from leader where in_charge = ?";


}
