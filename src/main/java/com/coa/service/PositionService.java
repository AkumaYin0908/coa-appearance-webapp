package com.coa.service;


import com.coa.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PositionService {


    Position findPositionByName(String name);
    Page<Position> findAll(Pageable pageable);
    List<Position> listAll();
    void save(Position position);
}
