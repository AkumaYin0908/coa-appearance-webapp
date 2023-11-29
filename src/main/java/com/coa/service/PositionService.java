package com.coa.service;


import com.coa.exceptions.PositionNotFoundException;
import com.coa.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PositionService {


    Optional<Position> findPositionByName(String name) throws PositionNotFoundException;
    Page<Position> findAll(Pageable pageable);
    List<Position> listAll();
    void save(Position position);
}
