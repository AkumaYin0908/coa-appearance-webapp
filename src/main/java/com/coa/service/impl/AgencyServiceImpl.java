package com.coa.service.impl;

import com.coa.exceptions.rest.AlreadyExistException;
import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.model.Agency;
import com.coa.payload.request.AgencyRequest;
import com.coa.payload.response.AgencyResponse;
import com.coa.repository.AgencyRepository;
import com.coa.service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<AgencyResponse> findAll() {
        return agencyRepository.findAll().stream().map(agency -> modelMapper.map(agency,AgencyResponse.class)).toList();
    }

    @Override
    public AgencyResponse findById(Long id) {
        Agency agency = agencyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Agency","id",id));
        return modelMapper.map(agency,AgencyResponse.class);
    }

    @Override
    public AgencyResponse findByName(String name) {
        Agency agency = agencyRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Agency","name",name));
        return modelMapper.map(agency,AgencyResponse.class);
    }

    @Override
    public List<Map<Long, String>> findNames() {
        return agencyRepository.findNames();
    }

    @Override
    public AgencyResponse save(AgencyRequest agencyRequest) {
        Optional<Agency> agencyOptional = agencyRepository.findByName(agencyRequest.getName());

        if(agencyOptional.isPresent()){
            throw new AlreadyExistException("Agency", "name");
        }

        Agency agency = modelMapper.map(agencyRequest,Agency.class);
        Agency dbAgency = agencyRepository.save(agency);

        return modelMapper.map(dbAgency,AgencyResponse.class);
    }

    @Override
    public AgencyResponse update(Long id, AgencyRequest agencyRequest) {

        Agency agency = agencyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Agency","id",id));

        Optional<Agency> agencyOptional = agencyRepository.findByName(agencyRequest.getName());

        if(agencyOptional.isPresent() && !agencyOptional.get().getId().equals(id)){
            throw new AlreadyExistException("Agency","name");
        }

        agency.setName(agencyRequest.getName());
        agencyRepository.save(agency);
        return modelMapper.map(agency,AgencyResponse.class);
    }

    @Override
    public void delete(Long id) {
        agencyRepository.deleteById(id);
    }
}
