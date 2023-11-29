package com.coa.service.impl;

import com.coa.exceptions.PositionNotFoundException;
import com.coa.model.Position;
import com.coa.repository.PositionRepository;
import com.coa.service.PositionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {


    private final PositionRepository positionRepository;


    @Override
    public Optional<Position> findPositionByName(String name) throws PositionNotFoundException {
        return positionRepository.findPositionByName(name);

    }

    @Override
    public Page<Position> findAll(Pageable pageable) {
       return positionRepository.findAll(pageable);
    }

    @Override
    public List<Position> listAll() {
       return positionRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Position position) {
        positionRepository.save(position);
    }
}
