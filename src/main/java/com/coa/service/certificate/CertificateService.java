package com.coa.service.certificate;

import com.coa.payload.request.AppearanceRequest;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public interface CertificateService {

    String generateSingleCertificate(AppearanceRequest appearanceRequest, int templateNo) throws JRException, IOException;
}
