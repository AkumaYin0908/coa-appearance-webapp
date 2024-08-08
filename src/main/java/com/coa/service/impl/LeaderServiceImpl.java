package com.coa.service.impl;

import com.coa.exceptions.rest.AlreadyExistException;
import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.model.Leader;
import com.coa.payload.request.LeaderRequest;
import com.coa.payload.response.LeaderResponse;
import com.coa.repository.LeaderRepository;
import com.coa.service.LeaderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaderServiceImpl implements LeaderService {

    private final LeaderRepository leaderRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<LeaderResponse> findAll() {
        return leaderRepository.findAll().stream().map(leader -> modelMapper.map(leader, LeaderResponse.class)).toList();
    }

    @Override
    public LeaderResponse findById(Long id) {
        Leader leader = leaderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leader", "id", id));
        return modelMapper.map(leader, LeaderResponse.class);
    }

    @Override
    public LeaderResponse findByName(String name) {
        Leader leader = leaderRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Leader", "name", name));
        return modelMapper.map(leader, LeaderResponse.class);
    }

    @Override
    public List<Map<Long, String>> findNames() {
        return leaderRepository.findNames();
    }

    @Override
    public Optional<LeaderResponse> findByStatus(boolean inCharge) {

        return leaderRepository.findByStatus(inCharge).map(leader -> modelMapper.map(leader, LeaderResponse.class));
    }

    @Override
    @Transactional
    public LeaderResponse updateStatus(boolean inCharge, Long id) {
        Leader leader = leaderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leader", "id", id));
        leader.setInCharge(inCharge);

        leaderRepository.save(leader);
        return modelMapper.map(leader, LeaderResponse.class);
    }

    @Override
    public LeaderResponse save(LeaderRequest leaderRequest) {
        Optional<Leader> leaderOptional = leaderRepository.findByName(leaderRequest.getName());

        if (leaderOptional.isPresent()) {
            throw new AlreadyExistException("Leader", "name");
        }

        Leader leader = modelMapper.map(leaderRequest, Leader.class);

        Leader dbLeader = leaderRepository.save(leader);

        return modelMapper.map(dbLeader, LeaderResponse.class);
    }

    @Override
    public LeaderResponse update(Long id, LeaderRequest leaderRequest) {
        Leader leader = leaderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leader", "id", id));

        Optional<Leader> leaderOptional = leaderRepository.findByName(leaderRequest.getName());

        if (leaderOptional.isPresent() && !leaderOptional.get().getId().equals(id)) {
            throw new AlreadyExistException("Leader", "name");
        }
        leader.setName(leaderRequest.getName());
        leader.setPosition(leaderRequest.getPosition());
        leader.setInCharge(leaderRequest.isInCharge());

        leaderRepository.save(leader);

        return modelMapper.map(leader, LeaderResponse.class);
    }

    @Override
    public void delete(Long id) {
        leaderRepository.deleteById(id);
    }
}
