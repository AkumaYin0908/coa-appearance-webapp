package com.coa.service.impl;

import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.model.Appearance;
import com.coa.model.Visitor;
import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.response.AppearanceResponse;
import com.coa.repository.AppearanceRepository;
import com.coa.repository.VisitorRepository;
import com.coa.service.AppearanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppearanceServiceImpl implements AppearanceService {
    private final AppearanceRepository appearanceRepository;
    private final ModelMapper modelMapper;


    @Override
    public AppearanceResponse findById(Long id) {
        Appearance appearance = appearanceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Appearance","id",id));
        return modelMapper.map(appearance,AppearanceResponse.class);
    }

    @Override
    public List<AppearanceResponse> findByVisitor(Long id) {
        return null;
    }

    @Override
    public List<AppearanceResponse> findByVisitorAndDateIssued(Long id, String strDateIssued) {
        return null;
    }

    @Override
    public List<AppearanceResponse> findByPurpose(String description) {
        return null;
    }

    @Override
    public List<AppearanceResponse> findByDateIssued(String strDateIssued) {
        return null;
    }

    @Override
    public List<AppearanceResponse> findByMonth(Integer month) {
        return null;
    }

    @Override
    public List<AppearanceResponse> findByYear(Integer year) {
        return null;
    }

    @Override
    public AppearanceResponse findByReference(String reference) {
        return null;
    }

    @Override
    public AppearanceResponse save(Long id, AppearanceRequest appearanceRequest) {
        return null;
    }

    @Override
    public AppearanceResponse update(Long id, AppearanceRequest appearanceRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
