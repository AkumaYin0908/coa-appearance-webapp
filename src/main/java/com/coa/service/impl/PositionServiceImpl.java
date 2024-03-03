package com.coa.service.impl;

import com.coa.model.Position;
import com.coa.repository.PositionRepository;
import com.coa.service.PositionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {


    private final PositionRepository positionRepository;


    @Override
    public Position findPositionByName(String name) {
        return positionRepository.findPositionByName(name).orElse(new Position(name));

    }

    @Cacheable(value = "position")
    @Override
    public Page<Position> findAll(Pageable pageable) {
       return positionRepository.findAll(pageable);
    }

    @Cacheable(value = "position")
    @Override
    public List<Position> findAll() {
       return positionRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Position position) {
        positionRepository.save(position);
    }
}
