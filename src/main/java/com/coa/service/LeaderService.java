package com.coa.service;

import com.coa.exceptions.LeaderNotFoundException;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Leader;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface LeaderService {


    Page<Leader> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Leader> findLeaderByName(String name) ;

    Optional<Leader> findLeaderByName(Long id, String name) ;

    Page<Leader> findAll(Pageable pageable);

    List<Leader> findAll();
    Leader findById(Long id) ;

    Leader findLeaderByInChargeStatus(boolean inCharge) ;


    void updateInCharge(Long id, boolean inCharge);

    void updateInCharge(Long  currentInCharge, Long newlyInCharge);

    Long countByInCharge();

    void save(Leader leader);

    void deleteById(Long id);



}
