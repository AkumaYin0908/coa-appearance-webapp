package com.coa.service;

import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.response.AppearanceResponse;

import java.util.List;
import java.util.Optional;

public interface AppearanceService {



    AppearanceResponse findById(Long id);

    List<AppearanceResponse> findAppearanceOrderByDateIssuedDESC();

    List<AppearanceResponse> findAll();

    List<AppearanceResponse> findByVisitor(Long id);

    List<AppearanceResponse> findByVisitorAndDateIssued(Long id, String strDateIssued);

    Optional<AppearanceResponse> findByVisitorAndDateFrom(Long id, String strDateFrom);

    List<AppearanceResponse> findByPurpose(String description);

    List<AppearanceResponse> findByDateIssued(String strDateIssued);

    List<AppearanceResponse> findByMonth(Integer month);

    List<AppearanceResponse> findByYear(Integer year);

    AppearanceResponse findByReference(String reference);

    AppearanceResponse save(AppearanceRequest appearanceRequest);

    List<AppearanceResponse> saveAll(List<AppearanceRequest> appearanceRequests);

    AppearanceResponse update(Long id, AppearanceRequest appearanceRequest);

    void delete(Long id);


}
