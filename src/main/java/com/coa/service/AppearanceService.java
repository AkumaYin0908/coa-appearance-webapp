package com.coa.service;

import com.coa.dto.AppearanceDTO;
import com.coa.model.Appearance;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AppearanceService {

    Page<Appearance> findByVisitorNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Appearance> findAll(Pageable pageable);


    List<Appearance> listAll();

    Optional<Appearance> findById(Long id);

    Optional<AppearanceDTO> findAndMapToAppearanceDTO(Long id);

    List<Appearance> listAppearanceByVisitor(Visitor visitor);

    void save(Appearance appearance);

    void deleteById(Long id);


}
