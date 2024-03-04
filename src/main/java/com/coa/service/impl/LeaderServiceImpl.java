package com.coa.service.impl;

import com.coa.exceptions.LeaderNotFoundException;
import com.coa.model.Leader;
import com.coa.repository.LeaderRepository;
import com.coa.service.LeaderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LeaderServiceImpl implements LeaderService {

    private final LeaderRepository leaderRepository;


    @Override
    public Page<Leader> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return leaderRepository.findByNameContainingIgnoreCase(name,pageable);
    }

    @Override
    public  Optional<Leader> findLeaderByName(String name)  {
        return leaderRepository.findLeaderByName(name);
    }

    @Override
    public  Optional<Leader> findLeaderByName(Long id, String name)  {
        return leaderRepository.findLeaderByName(id,name);
    }


    @Override
    public Page<Leader> findAll(Pageable pageable)  {
        return leaderRepository.findAll(pageable);
    }


    @Override
    public List<Leader> findAll() {
        return leaderRepository.findAll();
    }

    @Override
    public Leader findById(Long id) {
        return leaderRepository.findById(id).orElseThrow(()-> new LeaderNotFoundException("Leader not found!"));
    }

    @Override
    public Leader findLeaderByInChargeStatus(boolean inCharge)  {
        return leaderRepository.findLeaderByInChargeStatus(inCharge).orElse(null);
    }

    @Override
    @Transactional
    public void updateInCharge(Long id, boolean inCharge) {
        leaderRepository.updateInChargeStatus(id,inCharge);
    }

    @Override
    @Transactional
    public void updateInCharge(Long currentInCharge, Long newlyInCharge) {
        leaderRepository.updateInChargeStatus(currentInCharge,newlyInCharge);
    }

    @Override
    public Long countByInCharge() {
        return leaderRepository.countByInCharge();
    }

    @Override
    @Transactional
    public void save(Leader leader) {
        leaderRepository.save(leader);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        leaderRepository.deleteById(id);
    }
}
