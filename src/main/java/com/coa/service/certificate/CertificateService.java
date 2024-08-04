package com.coa.service.certificate;

import com.coa.payload.request.AppearanceRequest;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public interface CertificateService {

    String generateSingleCertificate(Long templateNo, AppearanceRequest appearanceRequest) throws JRException, IOException;
}
