package com.coa.service;

import com.coa.payload.request.PositionRequest;
import com.coa.payload.request.VisitorRequest;
import com.coa.payload.response.PositionResponse;
import com.coa.payload.response.VisitorResponse;

import java.util.List;
import java.util.Map;

public interface PositionService {


    List<PositionResponse> findAll();

    PositionResponse findById(Long id);

    PositionResponse findByTitle(String title);

    Map<Long,String> findTitles();

    PositionResponse save(PositionRequest positionRequest);

    PositionResponse update(Long id, PositionRequest positionRequest);

    void delete(Long id);

}
