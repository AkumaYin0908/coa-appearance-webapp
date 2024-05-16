package com.coa.service.address;

import com.coa.payload.response.address.AddressResponse;

import java.util.List;

public interface AddressService {

    List<AddressResponse> findAll();

    List<AddressResponse> findById(Long id);

    List<AddressResponse> findByBarangay(String brgyCode);

    List<AddressResponse>  findByMunicipality(String munCode);

    List<AddressResponse>  findByProvince(String proCode);

    List<AddressResponse>  findByRegion(String regionCode);

    AddressResponse findByCodes(String brgyCode, String munCode, String proCode, String region);
}
