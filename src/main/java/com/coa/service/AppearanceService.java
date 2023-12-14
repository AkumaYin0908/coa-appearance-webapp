package com.coa.service;

import com.coa.dto.AppearanceDTO;
import com.coa.model.Appearance;
import com.coa.model.Purpose;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppearanceService {

    Page<Appearance> findByVisitorNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Appearance> findAll(Pageable pageable);


    List<Appearance> listAll();

    Optional<Appearance> findById(Long id);

    Optional<AppearanceDTO> findAndMapToAppearanceDTO(Long id);

    Page<Appearance> findAppearanceByVisitor(Visitor visitor,  Pageable pageable);

    Page<Appearance> findByDateIssued(LocalDate dateIssued, Visitor visitor, Pageable pageable);

    void save(Appearance appearance);

    void deleteById(Long id);

    Page<Appearance> findByPurposeContainingIgnoreCase(String purpose, Visitor visitor, Pageable pageable);


    Page<Appearance> findAppearanceByMonthDateIssued(Integer month, Visitor visitor,Pageable pageable);


    Page<Appearance> findAppearanceByYearDateIssued(Integer year, Visitor visitor,Pageable pageable);

    Page<Appearance> findByPurposeAndMonthAndYearContainingIgnoreCase(String purpose, Integer  month, Integer year, Visitor visitor, Pageable pageable);

    Page<Appearance> findAppearanceByMonthAndYearDateIssued(Integer  month, Integer year, Visitor visitor, Pageable pageable);



    Page<Appearance> findByPurposeAndMonthContainingIgnoreCase(String purpose,Integer  month, Visitor visitor, Pageable pageable);


    Page<Appearance> findByPurposeAndYearContainingIgnoreCase(String purpose, Integer year, Visitor visitor, Pageable pageable);


    List<Integer> findAllDistinctYear();

    Page<Appearance> findAppearanceOrderByDateIssuedASC(Pageable pageable);


}
