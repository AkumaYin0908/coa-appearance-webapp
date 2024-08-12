package com.coa.service.certificate.impl;

import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.request.VisitorRequest;
import com.coa.payload.request.address.AddressRequest;
import com.coa.payload.response.AgencyResponse;
import com.coa.payload.response.AppearanceResponse;
import com.coa.payload.response.LeaderResponse;
import com.coa.payload.response.address.AddressResponse;
import com.coa.repository.AppearanceRepository;
import com.coa.service.LeaderService;
import com.coa.service.certificate.CertificateService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {


    private final AppearanceRepository appearanceRepository;
    private final ModelMapper modelMapper;
    private final LeaderService leaderService;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");


    //https://www.webscale.com/engineering-education/how-to-generate-reports-in-a-spring-boot-app-leveraging-jaspersoft/
    private JasperPrint getJasperPrint(List<AppearanceRequest> appearanceRequests, String appearanceType, InputStream inputStream) throws JRException, IOException {
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        Map<String, Object> parameters = new HashMap<>();
        List<AppearanceResponse> appearanceResponses = null;

        this.putVisitorDetails(parameters, appearanceRequests.get(0).getVisitor());
        this.putDateIssuedDetails(parameters, appearanceRequests.get(0).getDateIssued());

        if (appearanceType.equalsIgnoreCase("single")) {
            AppearanceResponse appearanceResponse = modelMapper.map(appearanceRequests.get(0), AppearanceResponse.class);
            parameters.put("dateOfTravel", appearanceResponse.getFormattedDateRange());
            parameters.put("purpose", appearanceResponse.getPurpose().getDescription());
            parameters.put("reference", appearanceResponse.getReference());
        } else if(appearanceType.equalsIgnoreCase("consolidated")) {
            appearanceResponses = new ArrayList<>();
            for(AppearanceRequest appearanceRequest : appearanceRequests){
                AppearanceResponse appearanceResponse = modelMapper.map(appearanceRequest, AppearanceResponse.class);
                appearanceResponses.add(appearanceResponse);
            }
        }

        InputStream logoInput = getClass().getResourceAsStream("/static/images/logo.png");
        InputStream headingInput = getClass().getResourceAsStream("/static/images/coa_heading.png");

        parameters.put("logo", logoInput);
        parameters.put("heading", headingInput);

        LeaderResponse leaderResponse = leaderService.findByStatus(true)
                .map(leader -> modelMapper.map(leader, LeaderResponse.class)).orElseThrow(() -> new ResourceNotFoundException("Leader", "inCharge", "true"));

        parameters.put("leaderName", leaderResponse.getName());
        parameters.put("leaderPosition", leaderResponse.getPosition());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,appearanceResponses == null ?  new JREmptyDataSource() : new JRBeanCollectionDataSource(appearanceResponses));
        return jasperPrint;
    }

    public void putVisitorDetails(Map<String, Object> parameters, VisitorRequest visitorRequest) {
        AddressRequest addressResponse = visitorRequest.getAddress();
        String fullName = String.format("%s%s%s", visitorRequest.getFirstName(),
                visitorRequest.getMiddleInitial().equalsIgnoreCase("n/a") ? " " : visitorRequest.getMiddleInitial() + ". ",
                visitorRequest.getLastName());

        String fullAddress = String.format("%s%s, %s", addressResponse.getBarangay() == null ? "" : String.format("%s, ", addressResponse.getBarangay().getName()),
                addressResponse.getMunicipality().getName(), addressResponse.getProvince().getName());

        String office = visitorRequest.getAgency() == null ? fullAddress : visitorRequest.getAgency().getName();

        parameters.put("courtesyTitle", visitorRequest.getCourtesyTitle().getTitle());
        parameters.put("fullName", fullName);

        parameters.put("position", visitorRequest.getPosition().getTitle());
        parameters.put("office", office);

        parameters.put("lastName", visitorRequest.getLastName());
    }

    private void putDateIssuedDetails(Map<String, Object> parameters, String strDateIssued) {
        LocalDate dateIssued = LocalDate.parse(strDateIssued, dateTimeFormatter);
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
