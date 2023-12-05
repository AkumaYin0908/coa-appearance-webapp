package com.coa.service.impl;

import com.coa.dto.AppearanceDTO;
import com.coa.model.Appearance;
import com.coa.model.Visitor;
import com.coa.repository.AppearanceRepository;
import com.coa.service.AppearanceService;
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
    public Page<Appearance> findByDateIssued(LocalDate dateIssued, Pageable pageable) {
        return appearanceRepository.findByDateIssued(dateIssued,pageable);
    }


    @Override
    public void save(Appearance appearance) {
        appearanceRepository.save(appearance);
    }

    @Override
    public void deleteById(Long id) {
        appearanceRepository.deleteById(id);
    }

    @Override
    public Page<Appearance> findByPurposeContainingIgnoreCase(String purpose, Pageable pageable) {
        return appearanceRepository.findByPurposeContainingIgnoreCase(purpose,pageable);
    }

    @Override
    public Page<Appearance> findAppearanceByMonthDateIssued(int month, Pageable pageable) {
        return appearanceRepository.findAppearanceByMonthDateIssued(month, pageable);
    }

    @Override
    public Page<Appearance> findAppearanceByYearDateIssued(int year, Pageable pageable) {
       return appearanceRepository.findAppearanceByYearDateIssued(year, pageable);
    }

    @Override
    public List<Integer> findAllDistinctYear() {
        return appearanceRepository.findAllDistinctYear();
    }
}
