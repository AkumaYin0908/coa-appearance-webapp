package com.coa.service.impl;

import com.coa.dto.AppearanceDTO;
import com.coa.model.Appearance;
import com.coa.model.Visitor;
import com.coa.repository.AppearanceRepository;
import com.coa.service.AppearanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AppearanceServiceImpl implements AppearanceService {

    private final AppearanceRepository appearanceRepository;

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
    public Optional<Appearance> findById(Long id) {
        return appearanceRepository.findById(id);
    }

    @Override
    public Optional<AppearanceDTO> findAndMapToAppearanceDTO(Long id) {
        return Optional.empty();
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
    public void save(Appearance appearance) {
        appearanceRepository.save(appearance);
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
}
