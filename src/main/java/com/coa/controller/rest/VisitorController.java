package com.coa.controller.rest;

import com.coa.payload.request.VisitorRequest;
import com.coa.payload.response.APIResponse;
import com.coa.payload.response.VisitorResponse;
import com.coa.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitors")
@CrossOrigin("http://localhost:8050")
public class VisitorController {

    private final VisitorService visitorService;


    @GetMapping(params = "!name")
    public ResponseEntity<List<VisitorResponse>> getAllVisitor(){

        return new ResponseEntity<>(visitorService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitorResponse> getVisitorById(@PathVariable("id")Long id){
        return new ResponseEntity<>(visitorService.findById(id),HttpStatus.FOUND);
    }

    @GetMapping("/names")
    public ResponseEntity<List<Map<Long, String>>> getAllVisitorName(){
        return new ResponseEntity<>(visitorService.findNames(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<VisitorResponse> getVisitorByName(@RequestParam(value = "firstName",required = false)String firstName,
                                                            @RequestParam(value = "middleInitial",required = false)String middleInitial,
                                                            @RequestParam(value = "lastName",required = false)String lastName){
         return new ResponseEntity<>(visitorService.findByName(firstName,middleInitial,lastName), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<VisitorResponse> saveVisitor(@RequestBody VisitorRequest visitorRequest){
        return new ResponseEntity<>(visitorService.save(visitorRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitorResponse> updateVisitor(@PathVariable("id")Long id, @RequestBody VisitorRequest visitorRequest){
        return new ResponseEntity<>(visitorService.update(id,visitorRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteVisitor(@PathVariable("id")Long id){
        visitorService.delete(id);
        return new ResponseEntity<>(new APIResponse("Deleted successfully!",true), HttpStatus.OK);
    }
}
