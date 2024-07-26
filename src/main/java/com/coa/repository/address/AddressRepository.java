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


    @Query(value = AddressConstant.FIND_BY_BARANGAY,nativeQuery = true)
    List<Address> findByBarangay(String brgyCode);

    @Query(value = AddressConstant.FIND_BY_MUNICIPALITY,nativeQuery = true)
    List<Address> findByMunicipality(String munCode);

    @Query(value = AddressConstant.FIND_BY_PROVINCE, nativeQuery = true)
    List<Address> findByProvince(String proCode);

    @Query(value = AddressConstant.FIND_BY_REGION, nativeQuery = true)
    List<Address> findByRegion(String regionCode);

    @Query(value = AddressConstant.FIND_BY_CODES, nativeQuery = true)
    Optional<Address> findByCodes(String brgyCode, String munCode, String proCode, String regionCode);


}
