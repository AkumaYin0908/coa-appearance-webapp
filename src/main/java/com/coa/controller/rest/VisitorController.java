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

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitors")
public class VisitorController {

    private final VisitorService visitorService;


    @GetMapping("/all")
    public ResponseEntity<List<VisitorResponse>> getAllVisitor(){
        List<VisitorResponse> visitors = visitorService.findAll();

        if(visitors.isEmpty()) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(visitors,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitorResponse> getVisitorById(@PathVariable("id")Long id){
        return new ResponseEntity<>(visitorService.findById(id),HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<VisitorResponse> getVisitorByName(@RequestParam("name") String name){
        return new ResponseEntity<>(visitorService.findByName(name), HttpStatus.FOUND);
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
        return new ResponseEntity<>(new APIResponse("Delete successfully!",true), HttpStatus.OK);
    }
}
