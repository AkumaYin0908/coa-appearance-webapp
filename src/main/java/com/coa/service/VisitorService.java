package com.coa.service;

import com.coa.exception.VisitorNotFoundException;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface VisitorService {

    Page<Visitor> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Visitor> findAll(Pageable pageable);

    List<Visitor> listAll();
    Visitor findById(Long id) throws VisitorNotFoundException;

    Visitor findVisitorByName(String name) throws VisitorNotFoundException;
    void save(Visitor visitor);

    void deleteById ( Long id);


}
