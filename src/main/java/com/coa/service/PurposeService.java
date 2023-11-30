package com.coa.service;

import com.coa.model.Purpose;

import java.util.List;
import java.util.Optional;

public interface PurposeService {


    Optional<Purpose> findByPurpose(String purpose);
    List<Purpose> findAll();
    void save(Purpose purpose);
}
