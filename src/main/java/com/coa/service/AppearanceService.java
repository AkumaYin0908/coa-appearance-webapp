package com.coa.service;

import com.coa.dto.AppearanceDTO;
import com.coa.exceptions.AppearanceNotFoundException;
import com.coa.model.Appearance;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppearanceService {

    Page<Appearance> findByVisitorNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Appearance> findAll(Pageable pageable);


    List<Appearance> listAll();

    List <Appearance> findAppearanceByVisitorAndDateIssued(Long id, LocalDate dateIssued);


    Appearance findById(Long id) throws AppearanceNotFoundException;


    AppearanceDTO findAndMapToAppearanceDTO(Long id) throws AppearanceNotFoundException;

    Page<Appearance> findAppearanceByVisitor(Visitor visitor,  Pageable pageable);

    Page<Appearance> findByDateIssued(LocalDate dateIssued, Visitor visitor, Pageable pageable);

    Appearance save(Appearance appearance);

    void saveAll(List<Appearance> appearances);

    void deleteById(Long id);

    Page<Appearance> findByPurposeContainingIgnoreCase(String purpose, Visitor visitor, Pageable pageable);


    Page<Appearance> findAppearanceByMonthDateIssued(Integer month, Visitor visitor,Pageable pageable);


    Page<Appearance> findAppearanceByYearDateIssued(Integer year, Visitor visitor,Pageable pageable);

    Page<Appearance> findByPurposeAndMonthAndYearContainingIgnoreCase(String purpose, Integer  month, Integer year, Visitor visitor, Pageable pageable);

    Page<Appearance> findAppearanceByMonthAndYearDateIssued(Integer  month, Integer year, Visitor visitor, Pageable pageable);



    Page<Appearance> findByPurposeAndMonthContainingIgnoreCase(String purpose,Integer  month, Visitor visitor, Pageable pageable);


    Page<Appearance> findByPurposeAndYearContainingIgnoreCase(String purpose, Integer year, Visitor visitor, Pageable pageable);


    List<Integer> findAllDistinctYear();

    Page<Appearance> findAppearanceOrderByDateIssuedDESC(Pageable pageable);

    Optional<Appearance> findByDateFromAndDateToAndName(LocalDate dateFrom,LocalDate dateTo,String name);



}
