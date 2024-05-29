package com.coa.service;

import com.coa.payload.request.AgencyRequest;
import com.coa.payload.response.AgencyResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AgencyService {

    List<AgencyResponse> findAll();

    AgencyResponse findById(Long id);

    AgencyResponse findByName(String name);

    List<Map<Long, String>> findNames();

    AgencyResponse save(AgencyRequest agency);

    AgencyResponse update(Long id, AgencyRequest agency);

    void delete(Long id);

}
