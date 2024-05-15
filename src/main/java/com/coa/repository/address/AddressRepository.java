package com.coa.repository.address;

import com.coa.constants.query_contants.AddressConstant;
import com.coa.model.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = AddressConstant.findByName, nativeQuery = true)
    Optional<Address> findByName(String name);

    @Query(value = AddressConstant.findByBarangay,nativeQuery = true)
    List<Address> findByBarangay(String brgyCode);

    @Query(value = AddressConstant.findByMunicipality,nativeQuery = true)
    List<Address> findByMunicipality(String munCode);

    @Query(value = AddressConstant.findByProvince, nativeQuery = true)
    List<Address> findByProvince(String proCode);

    @Query(value = AddressConstant.findByRegion, nativeQuery = true)
    List<Address> findByRegion(String regionCode);

    @Query(value = AddressConstant.findByCodes, nativeQuery = true)
    Optional<Address> findByCodes(String brgyCode, String munCode, String proCode, String regionCode);


}
