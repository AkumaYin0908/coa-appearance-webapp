package com.coa.repository;

import com.coa.constants.query_contants.PurposeConstant;
import com.coa.model.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PurposeRepository extends JpaRepository<Purpose,Long> {

    @Query(value = PurposeConstant.FIND_BY_DESCRIPTION,nativeQuery = true)
    Optional<Purpose> findByDescription(String description);

    @Query(value = PurposeConstant.FIND_DESCRIPTIONS,nativeQuery = true)
    List<Map<Long, String>> findDescriptions();
}
