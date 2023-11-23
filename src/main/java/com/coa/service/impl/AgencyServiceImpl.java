package com.coa.service.impl;


import com.coa.exception.AgencyNotFoundException;

import com.coa.model.Agency;
import com.coa.repository.AgencyRepository;
import com.coa.service.AgencyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl  implements AgencyService {

    private final AgencyRepository agencyRepository;

    @Override
    public Page<Agency> findAll(Pageable pageable) {
        return agencyRepository.findAll(pageable) ;
    }

    @Override
    public List<Agency> listAll() {
        return agencyRepository.findAll();
    }

    @Override
    public Agency findBy(Long id) throws AgencyNotFoundException {
        Optional<Agency> result=agencyRepository.findById(id);

        return result.isPresent() ? result.get() :
                result.orElseThrow(() -> new AgencyNotFoundException("Visitor with id no. " + id + " not found!"));
    }

    @Override
    public Agency findAgencyByName(String name) throws AgencyNotFoundException {
        Optional<Agency> result=agencyRepository.findAgencyName(name);

       return result.orElse(null);

    }

    @Override
    @Transactional
    public void save(Agency agency) {
        agencyRepository.save(agency);
    }
}
