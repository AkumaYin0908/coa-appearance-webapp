package com.coa.controller.rest;

import com.coa.model.Agency;
import com.coa.payload.request.AgencyRequest;
import com.coa.payload.response.APIResponse;
import com.coa.payload.response.AgencyResponse;
import com.coa.service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agencies")
public class AgencyController {
    private final AgencyService agencyService;

    @GetMapping(params = "!name")
    public ResponseEntity<List<AgencyResponse>> getAllAgency(){
        return new ResponseEntity<>(agencyService.findAll(),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyResponse> getAgencyById(@PathVariable("id")Long id){
        return new ResponseEntity<>(agencyService.findById(id),HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<AgencyResponse> getAgencyByName(@RequestParam(value = "name",required = false)String name){
        return new ResponseEntity<>(agencyService.findByName(name), HttpStatus.FOUND);
    }

    @GetMapping("/names")
    public ResponseEntity<List<Map<Long,String>>> getAgencyNames(){
        return new ResponseEntity<>(agencyService.findNames(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AgencyResponse> saveAgency(@RequestBody AgencyRequest agencyRequest){
        return new ResponseEntity<>(agencyService.save(agencyRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyResponse> updateAgency(@PathVariable("id")Long id, @RequestBody AgencyRequest agencyRequest){
        return new ResponseEntity<>(agencyService.update(id,agencyRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteAgency(@PathVariable("id")Long id){
        agencyService.delete(id);
        return new ResponseEntity<>(new APIResponse("Deleted successfully!",true), HttpStatus.OK);
    }
}
