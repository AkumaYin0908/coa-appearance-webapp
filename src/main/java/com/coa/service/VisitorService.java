package com.coa.service;

import com.coa.dto.VisitorDTO;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface VisitorService {

    Page<Visitor> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Visitor> findAll(Pageable pageable);

    List<Visitor> findAll();
    Visitor findById(Long id) throws VisitorNotFoundException;
   VisitorDTO findAndMapToVisitorDTO(Long id) throws VisitorNotFoundException;
   Optional<Visitor> findVisitorByName(String name) throws VisitorNotFoundException;

    Optional<Visitor> findVisitorByName(Long id, String name) throws VisitorNotFoundException;

    void save(Visitor visitor);

    void deleteById ( Long id);


}
