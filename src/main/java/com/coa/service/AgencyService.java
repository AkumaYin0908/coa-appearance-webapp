package com.coa.service;


import com.coa.model.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AgencyService {


    Page<Agency> findAll(Pageable pageable);
    List<Agency> listAll();
    Agency findBy(Long id);
    Agency findAgencyByName(String name);
    void save(Agency agency);
}
