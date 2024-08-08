package com.coa.controller.rest;


import com.coa.payload.request.LeaderRequest;
import com.coa.payload.response.APIResponse;
import com.coa.payload.response.LeaderResponse;
import com.coa.service.LeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaders")
public class LeaderController {
    private final LeaderService leaderService;

    @GetMapping(params = "!name")
    public ResponseEntity<List<LeaderResponse>> getAllLeader(){
        return new ResponseEntity<>(leaderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaderResponse> getLeaderById(@PathVariable("id")Long id){
        return new ResponseEntity<>(leaderService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<LeaderResponse> getLeaderByName(@RequestParam(value = "name",required = false)String name){
        return new ResponseEntity<>(leaderService.findByName(name), HttpStatus.FOUND);
    }

    @GetMapping("/names")
    public ResponseEntity<List<Map<Long,String>>>getLeaderNames(){
        return new ResponseEntity<>(leaderService.findNames(),HttpStatus.OK);
    }

    @GetMapping(params = {"!name","inCharge"})
    public ResponseEntity<LeaderResponse>getLeaderByStatus(@RequestParam boolean inCharge){
        Optional<LeaderResponse> leaderResponseOptional = leaderService.findByStatus(inCharge);
        return leaderResponseOptional.map(leaderResponse -> ResponseEntity.status(HttpStatus.FOUND).body(leaderResponse))
                .orElseGet(()->ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<LeaderResponse> saveLeader(@RequestBody LeaderRequest leaderRequest){
        return new ResponseEntity<>(leaderService.save(leaderRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaderResponse> updateLeader(@PathVariable("id")Long id, @RequestBody LeaderRequest leaderRequest){
        return new ResponseEntity<>(leaderService.update(id,leaderRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}/{inCharge}")
    public ResponseEntity<LeaderResponse>updateLeaderStatus(@PathVariable("id")Long id, @PathVariable("inCharge")boolean inCharge){
        return new ResponseEntity<>(leaderService.updateStatus(inCharge,id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteLeader(@PathVariable("id")Long id){
        leaderService.delete(id);
        return new ResponseEntity<>(new APIResponse("Deleted successfully!",true), HttpStatus.OK);
    }








}
