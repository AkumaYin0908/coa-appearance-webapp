package com.coa.constants.query_contants;

public interface AddressConstant {


    String findByBarangay  = "select * from address where barangay = ? ";

    String findByMunicipality = "select * from address where municipality = ?";

    String findByProvince = "select * from address where province = ?";

    String findByRegion = "select * from address where region = ?";

    String findByCodes = "select * from address where (barangay is null or barangay = ?) and municipality = ? " +
            "and province = ? and region = ?";


}
