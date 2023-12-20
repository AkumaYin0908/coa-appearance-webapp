package com.coa.service;

import com.coa.exceptions.LeaderNotFoundException;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Leader;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeaderService {


    Page<Leader> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Leader> findLeaderByName(String name) throws LeaderNotFoundException;

    Optional<Leader> findLeaderByName(Long id, String name) throws LeaderNotFoundException;


    Page<Leader> findAll(Pageable pageable);

    List<Leader> findAll();
    Optional<Leader> findById(Long id) throws LeaderNotFoundException;

    Optional<Leader> findLeaderByInChargeStatus(boolean inCharge) throws LeaderNotFoundException;


    void updateInCharge(Long id, boolean inCharge);

    void updateInCharge(Long  currentInCharge, Long newlyInCharge);

    Long countByInCharge();

    void save(Leader leader);

    void deleteById(Long id);



}
