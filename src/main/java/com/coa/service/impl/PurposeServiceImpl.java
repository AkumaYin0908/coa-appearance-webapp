package com.coa.service.impl;

import com.coa.model.Purpose;
import com.coa.repository.PurposeRepository;
import com.coa.service.PurposeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurposeServiceImpl implements PurposeService {

    private final PurposeRepository purposeRepository;

    @Override
    public Optional<Purpose> findByPurpose(String purpose) {
        return purposeRepository.findByPurpose(purpose);
    }

    @Override
    public List<Purpose> findAll() {
        return purposeRepository.findAll();
    }

    @Override
    public void save(Purpose purpose) {
        purposeRepository.save(purpose);
    }
}
