package com.coa.controller;

import com.coa.model.Position;
import com.coa.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurposeController {

    private final PositionService positionService;



    @GetMapping("/positions/getAll")
    public List<Position> getPositions(){
        return positionService.listAll();
    }
}
