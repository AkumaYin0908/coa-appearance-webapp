package com.coa.service;


import com.coa.exceptions.AgencyNotFoundException;
import com.coa.model.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AgencyService {


    Page<Agency> findAll(Pageable pageable);
    List<Agency> findAll();
    Agency findBy(Long id) throws AgencyNotFoundException;
    Agency findAgencyByName(String name) throws AgencyNotFoundException;
    void save(Agency agency);
}
