package com.coa.service.certificate.impl;

import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.payload.request.AppearanceRequest;

import com.coa.payload.response.AppearanceResponse;
import com.coa.payload.response.LeaderResponse;
import com.coa.payload.response.VisitorResponse;
import com.coa.payload.response.address.AddressResponse;
import com.coa.repository.AppearanceRepository;
import com.coa.service.LeaderService;
import com.coa.service.certificate.CertificateService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {


    private final AppearanceRepository appearanceRepository;
    private final ModelMapper modelMapper;
    private final LeaderService leaderService;



    //https://www.webscale.com/engineering-education/how-to-generate-reports-in-a-spring-boot-app-leveraging-jaspersoft/
    private JasperPrint getJasperPrint(List<AppearanceRequest> appearanceRequests, String appearanceType, InputStream inputStream) throws JRException, IOException {
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        Map<String, Object> parameters = new HashMap<>();

        List<AppearanceResponse> appearanceResponses = appearanceRequests.stream()
                .map(appearanceRequest -> modelMapper.map(appearanceRequest,AppearanceResponse.class)).toList();


        this.putVisitorDetails(parameters, appearanceResponses.get(0).getVisitor());
        this.putDateIssuedDetails(parameters, appearanceResponses.get(0).getDateIssued());


        if (appearanceType.equalsIgnoreCase("single")) {
            parameters.put("dateOfTravel", appearanceResponses.get(0).getFormattedDateRange());
            parameters.put("purpose", appearanceResponses.get(0).getPurpose().getDescription());
            parameters.put("reference", appearanceResponses.get(0).getReference());
        } else if(appearanceType.equalsIgnoreCase("consolidated")) {
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(appearanceResponses);
            parameters.put("appearances",jrBeanCollectionDataSource);
        }

        InputStream logoInput = getClass().getResourceAsStream("/static/images/logo.png");

        parameters.put("logo", logoInput);


        LeaderResponse leaderResponse = leaderService.findByStatus(true)
                .map(leader -> modelMapper.map(leader, LeaderResponse.class)).orElseThrow(() -> new ResourceNotFoundException("Leader", "inCharge", "true"));
        parameters.put("leaderName", leaderResponse.getName());
        parameters.put("leaderPosition", leaderResponse.getPosition());


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
        return jasperPrint;
    }

    public void putVisitorDetails(Map<String, Object> parameters, VisitorResponse visitorResponse) {
        AddressResponse addressResponse = visitorResponse.getAddress();
        String fullName = String.format("%s%s%s", visitorResponse.getFirstName(),
                visitorResponse.getMiddleInitial().equalsIgnoreCase("n/a") ? " " : visitorResponse.getMiddleInitial() + ". ",
                visitorResponse.getLastName());

        String fullAddress = String.format("%s%s, %s", addressResponse.getBarangay() == null ? "" : String.format("%s, ", addressResponse.getBarangay().getName()),
                addressResponse.getMunicipality().getName(), addressResponse.getProvince().getName());

        String office = visitorResponse.getAgency() == null ? fullAddress : visitorResponse.getAgency().getName();

        parameters.put("courtesyTitle", visitorResponse.getCourtesyTitle().getTitle());
        parameters.put("fullName", fullName);

        parameters.put("position", visitorResponse.getPosition().getTitle());
        parameters.put("office", office);

        parameters.put("lastName", visitorResponse.getLastName());
    }

    private void putDateIssuedDetails(Map<String, Object> parameters, String strDateIssued) {
        LocalDate dateIssued = LocalDate.parse(strDateIssued);
        parameters.put("day", getDayWithOrdinal(dateIssued.getDayOfMonth()));
        parameters.put("month", String.format("%s%s", dateIssued.getMonth().toString().charAt(0),
                dateIssued.getMonth().toString().toLowerCase().substring(1)));
        parameters.put("year", String.valueOf(dateIssued.getYear()));
    }

    @Override
    public String generateCertificate(Long templateNo, String appearanceType, List<AppearanceRequest> appearanceRequests) throws JRException, IOException {

        InputStream inputStream = getClass().getResourceAsStream(String.format("/static/certificates/CA_Template_%s_%d.jrxml", appearanceType, templateNo));

        JasperPrint jasperPrint = getJasperPrint(appearanceRequests, appearanceType, inputStream);

        String fileName = "certificate.pdf";
        Path uploadPath = getUploadPath("pdf", jasperPrint, fileName);
        return getPdfFileLink(uploadPath.toString());
    }

    private Path getUploadPath(String fileFormat, JasperPrint jasperPrint, String fileName) throws IOException, JRException {
        String uploadDir = StringUtils.cleanPath("./generated-certificates");
        Path uploadPath = Paths.get(uploadDir);


        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        //generate report and save it in the just created folder
        if (fileFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, String.format("%s/%s", uploadPath, fileName));
        }

        return uploadPath;

    }

    private String getPdfFileLink(String uploadPath) {
        return String.format("%s/%s", uploadPath, "certificate.pdf");
    }


    private static String getDayWithOrdinal(int day) {

        String suffix = "th";
        if (day % 10 == 1 && day % 100 != 11) suffix = "st";
        if (day % 10 == 2 && day % 100 != 12) suffix = "nd";
        if (day % 10 == 3 && day % 100 != 13) suffix = "rd";

        return String.format("%d%s", day, suffix);
    }


}
