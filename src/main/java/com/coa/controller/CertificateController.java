package com.coa.controller;


import com.coa.payload.request.AppearanceRequest;
import com.coa.service.certificate.CertificateService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;


    @PostMapping("/single-certificate/{templateNo}")
    public String generateSingleCertificate(@PathVariable("templateNo")int templateNo, @RequestBody AppearanceRequest appearanceRequest) throws JRException, IOException {
        String fileLink = certificateService.generateSingleCertificate(appearanceRequest,templateNo);

        return String.format("redirect:/%s",fileLink);
    }
}
