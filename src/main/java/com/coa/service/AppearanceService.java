package com.coa.service;

import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.response.AppearanceResponse;

import java.util.List;

public interface AppearanceService {

    AppearanceResponse findById(Long id);
    List<AppearanceResponse> findByVisitor(Long id);

    List<AppearanceResponse> findByVisitorAndDateIssued(Long id, String strDateIssued);

    List<AppearanceResponse> findByPurpose(String description);

    List<AppearanceResponse> findByDateIssued(String strDateIssued);

    List<AppearanceResponse> findByMonth(Integer month);

    List<AppearanceResponse> findByYear(Integer year);

    AppearanceResponse findByReference(String reference);

    AppearanceResponse save(AppearanceRequest appearance);

    AppearanceResponse update(Long id, AppearanceRequest appearance);

    void delete(Long id);


}
