package com.coa.repository;

import com.coa.constants.query_contants.LeaderConstant;
import com.coa.model.Leader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface LeaderRepository extends JpaRepository<Leader,Long> {

    @Query(value = LeaderConstant.FIND_BY_NAME,nativeQuery = true)
    Optional<Leader> findByName(String name);

    @Query(value = LeaderConstant.FIND_NAMES,nativeQuery = true)
    List<Map<Long, String>> findNames();

    @Query(value = LeaderConstant.FIND_BY_STATUS,nativeQuery = true)
    Optional<Leader> findByStatus(boolean inCharge);

    @Query(value = LeaderConstant.UPDATE_STATUS, nativeQuery = true)
    @Modifying
    void updateStatus(boolean inCharge, Long id);
}
