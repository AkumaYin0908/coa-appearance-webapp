package com.coa.service.impl;

import com.coa.exceptions.rest.AlreadyExistException;
import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.model.Position;
import com.coa.model.Purpose;
import com.coa.payload.request.PurposeRequest;
import com.coa.payload.response.PurposeResponse;
import com.coa.repository.PurposeRepository;
import com.coa.service.PurposeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurposeServiceImpl implements PurposeService {

    private final PurposeRepository purposeRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<PurposeResponse> findAll() {
        return purposeRepository.findAll().stream().map(purpose -> modelMapper.map(purpose,PurposeResponse.class)).toList();
    }

    @Override
    public PurposeResponse findById(Long id) {
        Purpose purpose = purposeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Purpose","id",id));
        return modelMapper.map(purpose,PurposeResponse.class);
    }

    @Override
    public PurposeResponse findByDescription(String description) {
        Purpose purpose = purposeRepository.findByDescription(description).orElseThrow(()->new ResourceNotFoundException("Purpose","description",description));
        return modelMapper.map(purpose, PurposeResponse.class);
    }

    @Override
    public List<Map<Long, String>> findDescriptions() {
        return purposeRepository.findDescriptions();
    }

    @Override
    @Transactional
    public PurposeResponse save(PurposeRequest purposeRequest) {
        Optional<Purpose> purposeOptional = purposeRepository.findByDescription(purposeRequest.getDescription());

        if(purposeOptional.isPresent()){
            throw new AlreadyExistException("Purpose", "description");
        }
        Purpose purpose = modelMapper.map(purposeRequest, Purpose.class);
        Purpose dbPurpose = purposeRepository.save(purpose);
        return modelMapper.map(dbPurpose, PurposeResponse.class);
    }

    @Override
    @Transactional
    public PurposeResponse update(Long id, PurposeRequest purposeRequest) {
        Purpose purpose = purposeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Purpose","id",id));

        Optional<Purpose> purposeOptional = purposeRepository.findByDescription(purposeRequest.getDescription());

        if(purposeOptional.isPresent() && purposeOptional.get().getId().equals(id)){
            throw new AlreadyExistException("Purpose","description");
        }

        purpose.setDescription(purposeRequest.getDescription());

        purposeRepository.save(purpose);

        return modelMapper.map(purpose,PurposeResponse.class);
    }

    @Override
    public void delete(Long id) {
        purposeRepository.deleteById(id);
    }
}
