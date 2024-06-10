package com.coa.controller.rest;

import com.coa.payload.request.PurposeRequest;
import com.coa.payload.response.APIResponse;
import com.coa.payload.response.PurposeResponse;
import com.coa.service.PurposeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purposes")
public class PurposeController {
    private final PurposeService purposeService;


    @GetMapping(params = "!description")
    public ResponseEntity<List<PurposeResponse>> getAllPurpose(){
        return new ResponseEntity<>(purposeService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurposeResponse> getPurposeById(@PathVariable("id") Long id){
        return new ResponseEntity<>(purposeService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<PurposeResponse> getPurposeByDescription(@RequestParam(value = "description",required = false)String description){
        return new ResponseEntity<>(purposeService.findByDescription(description), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<PurposeResponse> savePurpose(@RequestBody PurposeRequest purposeRequest){
        return new ResponseEntity<>(purposeService.save(purposeRequest), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<PurposeResponse> updatePurpose(@PathVariable("id") Long id, @RequestBody PurposeRequest purposeRequest){
        return new ResponseEntity<>(purposeService.update(id,purposeRequest), HttpStatus.OK);
    }

    @GetMapping("/descriptions")
    public ResponseEntity<List<Map<Long,String>>> getAllPurposeDescription(){
        return new ResponseEntity<>(purposeService.findDescriptions(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deletePurpose(@PathVariable("id") Long id){
        purposeService.delete(id);
        return new ResponseEntity<>(new APIResponse("Deleted successfull!",true), HttpStatus.OK);
    }




}
