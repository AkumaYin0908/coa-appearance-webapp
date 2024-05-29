package com.coa.service;

import com.coa.payload.request.PositionRequest;
import com.coa.payload.response.PositionResponse;
import java.util.List;
import java.util.Map;

public interface PositionService {


    List<PositionResponse> findAll();

    PositionResponse findById(Long id);

    PositionResponse findByTitle(String title);

    List<Map<Long, String>> findTitles();

    PositionResponse save(PositionRequest positionRequest);

    PositionResponse update(Long id, PositionRequest positionRequest);

    void delete(Long id);

}
