package com.coa.repository;

import com.coa.constants.query_contants.AgencyConstant;
import com.coa.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {


    @Query(value = AgencyConstant.FIND_BY_NAME,nativeQuery = true)
    Optional<Agency> findByName(String name);

    @Query(value = AgencyConstant.FIND_NAMES,nativeQuery = true)
    List<Map<Long, String>> findNames();
}
