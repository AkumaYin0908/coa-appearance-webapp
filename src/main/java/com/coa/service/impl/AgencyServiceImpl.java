package com.coa.service.impl;



import com.coa.exceptions.AgencyNotFoundException;
import com.coa.model.Agency;
import com.coa.repository.AgencyRepository;
import com.coa.service.AgencyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl  implements AgencyService {

    private final AgencyRepository agencyRepository;

    @Cacheable(value = "agency")
    @Override
    public Page<Agency> findAll(Pageable pageable) {
        return agencyRepository.findAll(pageable) ;
    }


    @Cacheable(value = "agency")
    @Override
    public List<Agency> findAll() {
        return agencyRepository.findAll();
    }

    @Cacheable(value = "agency")
    @Override
    public Agency findBy(Long id) throws AgencyNotFoundException {
        return agencyRepository.findById(id).orElseThrow(()->new AgencyNotFoundException("Agency not found!"));
    }

    @Cacheable(value = "agency")
    @Override
    public Agency findAgencyByName(String name){
        return agencyRepository.findAgencyName(name).orElse(new Agency(name));
    }

    @Override
    @Transactional
    public void save(Agency agency) {
        agencyRepository.save(agency);
    }
}
