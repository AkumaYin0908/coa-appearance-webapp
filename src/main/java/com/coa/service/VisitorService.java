package com.coa.service;


import com.coa.payload.request.VisitorRequest;
import com.coa.payload.response.VisitorResponse;

import java.util.List;
import java.util.Map;


public interface VisitorService {

    List<VisitorResponse> findAll();
    VisitorResponse findById(Long id);
    VisitorResponse findByName(String firstName, String middleInitial, String lastName);

    VisitorResponse save(VisitorRequest visitorRequest);

    VisitorResponse update(Long id, VisitorRequest visitorRequest);

    List<Map<Long, String>> findNames();

    void delete(Long id);



}
