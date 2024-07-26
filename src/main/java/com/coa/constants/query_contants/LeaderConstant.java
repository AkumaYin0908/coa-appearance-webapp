package com.coa.constants.query_contants;

public interface LeaderConstant {

    String FIND_BY_NAME = "select * from leader where name = ?";

    String FIND_NAMES = "select id, name from leader";

    String UPDATE_STATUS = "update leader set in_charge = ? where id = ?";

    String FIND_BY_STATUS = "select * from leader where in_charge = ?";


}
