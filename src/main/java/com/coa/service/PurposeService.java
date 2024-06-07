package com.coa.service;

import com.coa.payload.request.PurposeRequest;
import com.coa.payload.response.PurposeResponse;

import java.util.List;
import java.util.Map;

public interface PurposeService {

    List<PurposeResponse> findAll();

    PurposeResponse findById(Long id);

    PurposeResponse findByDescription(String description);

    List<Map<Long,String>> findDescriptions();

    PurposeResponse save(PurposeRequest purpose);

    PurposeResponse update(Long id, PurposeRequest purpose);

    void delete(Long id);




}
