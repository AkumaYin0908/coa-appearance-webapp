package com.coa.service.certificate;

import com.coa.payload.request.AppearanceRequest;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;

public interface CertificateService {

    String generateCertificate(Long templateNo,String appearanceType, List<AppearanceRequest> appearanceRequests) throws JRException, IOException;
}
