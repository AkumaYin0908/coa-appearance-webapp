package com.coa.controller;


import com.coa.payload.request.AppearanceRequest;
import com.coa.service.certificate.CertificateService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;


    @PostMapping("/certificate/{templateNo}")
    @ResponseBody
    public Map<String, String> generateCertificate(@PathVariable("templateNo") Long templateNo,@RequestParam("appearanceType")String appearanceType, @RequestBody List<AppearanceRequest> appearanceRequests) throws JRException, IOException {
        String fileLink = certificateService.generateCertificate(templateNo,appearanceType, appearanceRequests);


        Map<String, String> response = new HashMap<>();
        response.put("fileLink", fileLink);

        return response;
    }
}
