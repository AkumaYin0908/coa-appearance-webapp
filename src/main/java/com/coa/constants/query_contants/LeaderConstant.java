package com.coa.constants.query_contants;

public interface LeaderConstant {

    String findByName = "select * leader where name = ?";

    String findNames  = "select id, name from leader";

    String updateStatus = "update leader set in_charge = ? where id = ?";

    String findByStatus = "select * from leader where in_charge = ?";


}
