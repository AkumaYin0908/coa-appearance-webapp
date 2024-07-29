package com.coa.service.certificate.impl;

import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.response.AppearanceResponse;
import com.coa.service.certificate.CertificateService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {


    //https://www.webscale.com/engineering-education/how-to-generate-reports-in-a-spring-boot-app-leveraging-jaspersoft/
    private JasperPrint getJasperPrint(AppearanceResponse appearanceResponse){
        return null;
    }
    @Override
    public String generateSingleCertificate(AppearanceRequest appearanceRequest) throws JRException, IOException {
        return null;
    }

    private Path getUploadPath(String fileFormat, JasperPrint jasperPrint, String fileName) throws IOException, JRException{
        String uploadDir = StringUtils.cleanPath("./generated-certificates");
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        //generate report and save it in the just created folder
        if(fileFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint,String.format("%s/%s",uploadPath,fileName));
        }

        return uploadPath;

    }


}
