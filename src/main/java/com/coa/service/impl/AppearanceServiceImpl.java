package com.coa.service.impl;

import com.coa.dto.AppearanceDTO;
import com.coa.exceptions.AppearanceNotFoundException;
import com.coa.model.Appearance;
import com.coa.model.Visitor;
import com.coa.repository.AppearanceRepository;
import com.coa.service.AppearanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AppearanceServiceImpl implements AppearanceService {

    private final AppearanceRepository appearanceRepository;




    private static final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM dd, yyyy");



    @Override
    public Page<Appearance> findByVisitorNameContainingIgnoreCase(String name, Pageable pageable) {
        return  appearanceRepository.findByVisitorNameContainingIgnoreCase(name,pageable);
    }


    @Override
    public Page<Appearance> findAll(Pageable pageable) {
        return appearanceRepository.findAll(pageable);
    }


    @Override
    public List<Appearance> listAll() {
     return appearanceRepository.findAll();
    }

    @Override
    public List<Appearance> findAppearanceByVisitorAndDateIssued(Long id, LocalDate dateIssued) {
        return appearanceRepository.findAppearanceByVisitorAndDateIssued(id,dateIssued);
    }

    @Override
    public void saveAll(List<Appearance> appearances) {
        appearanceRepository.saveAllAndFlush(appearances);
    }


    @Override
    public Appearance findById(Long id) throws AppearanceNotFoundException {
        return appearanceRepository.findById(id).orElseThrow(()->new AppearanceNotFoundException("Appearance not found!"));
    }




    @Override
    public AppearanceDTO findAndMapToAppearanceDTO(Long id) throws AppearanceNotFoundException {
       Optional<Appearance> appearanceOptional = appearanceRepository.findById(id);
       AppearanceDTO appearanceDTO;

       if(appearanceOptional.isPresent()){
           Appearance appearance = appearanceOptional.get();

           appearanceDTO=new AppearanceDTO(
                   appearance.getId(),
                   appearance.getVisitor().getName(),
                   appearance.getVisitor().getPosition().getName(),
                   appearance.getVisitor().getAgency().getName(),
                   appearance.getDateIssued().format(dateTimeFormatter),
                   appearance.getDateFrom().format(dateTimeFormatter),
                   appearance.getDateTo().format(dateTimeFormatter),
                   appearance.getPurpose().getPurpose());



       }else{
           throw new AppearanceNotFoundException("Appearance not found!");
       }
        return appearanceDTO;
    }


    @Override
    public Page<Appearance> findAppearanceByVisitor(Visitor visitor,  Pageable pageable) {
        return appearanceRepository.findAppearanceByVisitor(visitor,pageable);
    }

    @Override
    public Page<Appearance> findByDateIssued(LocalDate dateIssued, Visitor visitor, Pageable pageable) {
        return appearanceRepository.findByDateIssued(dateIssued, visitor, pageable);
    }



    @Override
    @Transactional
    public Appearance save(Appearance appearance) {
        return appearanceRepository.saveAndFlush(appearance);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        appearanceRepository.deleteById(id);
    }


    @Override
    public Page<Appearance> findByPurposeContainingIgnoreCase(String purpose, Visitor visitor,Pageable pageable) {
        return appearanceRepository.findByPurposeContainingIgnoreCase(purpose,visitor,pageable);
    }


    @Override
    public Page<Appearance> findAppearanceByMonthDateIssued(Integer month, Visitor visitor, Pageable pageable) {
        return appearanceRepository.findAppearanceByMonthDateIssued(month, visitor, pageable);
    }


    @Override
    public Page<Appearance> findAppearanceByYearDateIssued(Integer year,Visitor visitor, Pageable pageable) {
       return appearanceRepository.findAppearanceByYearDateIssued(year, visitor, pageable);
    }


    @Override
    public Page<Appearance> findByPurposeAndMonthAndYearContainingIgnoreCase(String purpose, Integer month, Integer year,Visitor visitor, Pageable pageable) {
        return appearanceRepository.findByPurposeAndMonthAndYearContainingIgnoreCase(purpose, month, year, visitor, pageable);
    }


    @Override
    public Page<Appearance> findAppearanceByMonthAndYearDateIssued(Integer month, Integer year, Visitor visitor, Pageable pageable) {
        return appearanceRepository.findAppearanceByMonthAndYearDateIssued(month, year, visitor, pageable);
    }


    @Override
    public Page<Appearance> findByPurposeAndMonthContainingIgnoreCase(String purpose, Integer month, Visitor visitor, Pageable pageable) {
        return appearanceRepository.findByPurposeAndMonthContainingIgnoreCase(purpose, month, visitor, pageable);
    }


    @Override
    public Page<Appearance> findByPurposeAndYearContainingIgnoreCase(String purpose, Integer year, Visitor visitor,Pageable pageable) {
        return appearanceRepository.findByPurposeAndYearContainingIgnoreCase(purpose, year, visitor, pageable);
    }


    @Override
    public List<Integer> findAllDistinctYear() {
        return appearanceRepository.findAllDistinctYear();
    }


    @Override
    public Page<Appearance> findAppearanceOrderByDateIssuedDESC(Pageable pageable) {
        return appearanceRepository.findAppearanceOrderByDateIssuedDESC(pageable);
    }


    @Override
    public Optional<Appearance> findByDateFromAndDateToAndName(LocalDate dateFrom, LocalDate dateTo, String name) {
        return appearanceRepository.findByDateFromAndDateToAndName(dateFrom,dateTo,name);
    }
}
