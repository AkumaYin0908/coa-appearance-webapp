package com.coa.constants.query_contants;

public interface AgencyConstant {

    String findByName = "select * from agency where name = ?";

    String findNames = "select id, name from agency";

    /* for future reference
    String findConctenatedAgencyAndAddress = "SELECT " +
            "a.id AS agency_id, " +
            "ad.id AS address_id, " +
            "CONCAT(COALESCE(a.name,''), ' - ', " +
            "COALESCE(ad.barangay,''), ', ', " +
            "COALESCE(ad.municipality), ', ', " +
            "COALESCE(ad.province)) AS full_address " +
            "FROM agency a " +
            "JOIN agency_address aa ON a.id = aa.agency.id " +
            "JOIN address ad ON ad.id = aa.address.id";*/



}
