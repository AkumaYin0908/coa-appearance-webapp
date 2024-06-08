package com.coa.controller.rest;


import com.coa.payload.response.AppearanceResponse;
import com.coa.service.AppearanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppearanceController {


    private final AppearanceService appearanceService;



    @GetMapping("/visitors/{id}/appearances")
    public ResponseEntity<List<AppearanceResponse>> getByVisitor(@PathVariable("id")Long id){

        List<AppearanceResponse> appearances = appearanceService.findByVisitor(id);
        if(appearances.isEmpty()) return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(appearances, HttpStatus.OK);
    }

    @GetMapping("/visitors/{id}/appearances")
    public ResponseEntity<List<AppearanceResponse>> getByVisitorAndDateIssued(@PathVariable("id")Long id, @RequestParam("dateIssued") String strDateIssued){
        List<AppearanceResponse> appearances = appearanceService.findByVisitorAndDateIssued(id,strDateIssued);

        if(appearances.isEmpty()) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(appearances,HttpStatus.OK);
    }


    @GetMapping("/appearances")
    public ResponseEntity<List<AppearanceResponse>> getByPurpose(@RequestParam("description")String description){
        List<AppearanceResponse> appearances = appearanceService.findByPurpose(description);

        if(appearances.isEmpty()) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);

        return  new ResponseEntity<>(appearances,HttpStatus.OK);
    }
}
