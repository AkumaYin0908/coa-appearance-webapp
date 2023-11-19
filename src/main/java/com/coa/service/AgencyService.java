package com.coa.service;

import com.coa.exception.AgencyNotFoundException;
import com.coa.exception.VisitorNotFoundException;
import com.coa.model.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AgencyService {


    Page<Agency> findAll(Pageable pageable);
    List<Agency> listAll();
    Agency findBy(Long id) throws VisitorNotFoundException, AgencyNotFoundException;
    Agency findAgencyByName(String name) throws AgencyNotFoundException;
    void save(Agency agency);
}
