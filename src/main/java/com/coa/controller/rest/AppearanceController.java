package com.coa.controller.rest;


import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.response.APIResponse;
import com.coa.payload.response.AppearanceResponse;
import com.coa.service.AppearanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class AppearanceController {


    private final AppearanceService appearanceService;

    @GetMapping("/appearances")
    public ResponseEntity<List<AppearanceResponse>> getAllAppearance() {
        return new ResponseEntity<>(appearanceService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/visitors/{id}/appearances", params = "!dateIssued")
    public ResponseEntity<List<AppearanceResponse>> getByVisitor(@PathVariable("id") Long id) {
        return new ResponseEntity<>(appearanceService.findByVisitor(id), HttpStatus.OK);
    }

    @GetMapping(value = "/visitors/{id}/appearances", params = "dateIssued")
    public ResponseEntity<List<AppearanceResponse>> getByVisitorAndDateIssued(@PathVariable("id") Long id, @RequestParam("dateIssued") String strDateIssued) {

        return new ResponseEntity<>(appearanceService.findByVisitorAndDateIssued(id, strDateIssued), HttpStatus.OK);
    }


    @GetMapping(value = "/appearances", params = {"description","!dateIssued"})
    public ResponseEntity<List<AppearanceResponse>> getByPurpose(@RequestParam(value = "description", required = false) String description) {

        return new ResponseEntity<>(appearanceService.findByPurpose(description), HttpStatus.OK);
    }

    @GetMapping(value = "/appearances", params = "dateIssued")
    public ResponseEntity<List<AppearanceResponse>> getByDateIssued(@RequestParam(value = "dateIssued", required = false) String strDateIssued) {
        return new ResponseEntity<>(appearanceService.findByDateIssued(strDateIssued), HttpStatus.OK);
    }

    @GetMapping(value = "/appearances", params = "reference")
    public ResponseEntity<AppearanceResponse> getByReference(@RequestParam(value = "reference", required = false) String reference) {
        return new ResponseEntity<>(appearanceService.findByReference(reference), HttpStatus.FOUND);
    }

//    @PostMapping("/visitors/{id}/appearances")
//    public ResponseEntity<AppearanceResponse> saveAppearance(@PathVariable("id") Long id, @RequestBody AppearanceRequest appearanceRequest) {
//        return new ResponseEntity<>(appearanceService.save(id, appearanceRequest), HttpStatus.CREATED);
//    }

    @PostMapping(value = "/visitors/{id}/appearances",params = "!dateIssued")
    public ResponseEntity<List<AppearanceResponse>> saveAppearances(@PathVariable("id") Long id, @RequestBody List<AppearanceRequest> appearanceRequests,
                                                                    @RequestParam("appearanceType") String appearanceType) {
        if (appearanceType.equals("single")) {
            return new ResponseEntity<>(List.of(appearanceService.save(id, appearanceRequests.get(0))), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(appearanceService.saveAll(id, appearanceRequests), HttpStatus.CREATED);
        }

    }

    @PutMapping("/visitors/{id}/appearances")
    public ResponseEntity<AppearanceResponse> updateAppearance(@PathVariable("id") Long id, @RequestBody AppearanceRequest appearanceRequest) {
        return new ResponseEntity<>(appearanceService.update(id, appearanceRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/appearances/{id}")
    public ResponseEntity<APIResponse> deleteAppearance(@PathVariable("id") Long id) {
        appearanceService.delete(id);
        return new ResponseEntity<>(new APIResponse("Deleted successfully!", true), HttpStatus.OK);
    }


}
