package com.coa.service;

import com.coa.payload.response.LeaderResponse;

import java.util.List;
import java.util.Map;

public interface LeaderService {

    List<LeaderResponse> findAll();

    LeaderResponse findById(Long id);

    LeaderResponse findByName(String name);

    Map<Long,String> findNames();

    LeaderResponse findByStatus(boolean inCharge);

    LeaderResponse updateStatus(boolean inCharge, Long id);




}
