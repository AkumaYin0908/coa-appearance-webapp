package com.coa.controller.rest;

import com.coa.payload.request.PositionRequest;
import com.coa.payload.response.APIResponse;
import com.coa.payload.response.PositionResponse;
import com.coa.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;


    @GetMapping("/all")
    public ResponseEntity<List<PositionResponse>> getAllPosition(){
        List<PositionResponse> positions = positionService.findAll();

        if(positions.isEmpty()) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponse> getPositionById(@PathVariable("id")Long id){
        return new ResponseEntity<>(positionService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<PositionResponse> getPositionByTitle(@RequestParam("title")String title){
        return new ResponseEntity<>(positionService.findByTitle(title), HttpStatus.FOUND);
    }

    @GetMapping("/titles")
    public ResponseEntity<List<Map<Long,String>>> getAllPositionTitle(){
        return new ResponseEntity<>(positionService.findTitles(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PositionResponse> savePosition(@RequestBody PositionRequest positionRequest){
        return new ResponseEntity<>(positionService.save(positionRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionResponse> updatePosition(@PathVariable("id")Long id, @RequestBody PositionRequest positionRequest){
        return  new ResponseEntity<>(positionService.update(id,positionRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deletePosition(@PathVariable("id")Long id){
        positionService.delete(id);
        return new ResponseEntity<>(new APIResponse("Deleted successfully!",true), HttpStatus.OK);
    }
}
