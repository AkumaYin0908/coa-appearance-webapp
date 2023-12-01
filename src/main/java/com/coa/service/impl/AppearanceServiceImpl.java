package com.coa.service.impl;

import com.coa.dto.AppearanceDTO;
import com.coa.model.Appearance;
import com.coa.model.Visitor;
import com.coa.repository.AppearanceRepository;
import com.coa.service.AppearanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public List<Appearance> listAppearanceByVisitor(Visitor visitor) {
        return appearanceRepository.listAppearanceByVisitor(visitor);
    }

    @Override
    public void save(Appearance appearance) {
        appearanceRepository.save(appearance);
    }

    @Override
    public void deleteById(Long id) {
        appearanceRepository.deleteById(id);
    }
}
