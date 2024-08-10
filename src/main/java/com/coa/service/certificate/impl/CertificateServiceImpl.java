package com.coa.service.certificate.impl;

import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.request.VisitorRequest;
import com.coa.payload.request.address.AddressRequest;
import com.coa.payload.response.AgencyResponse;
import com.coa.payload.response.LeaderResponse;
import com.coa.payload.response.address.AddressResponse;
import com.coa.repository.AppearanceRepository;
import com.coa.service.LeaderService;
import com.coa.service.certificate.CertificateService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {


    private final AppearanceRepository appearanceRepository;
    private final ModelMapper modelMapper;
    private  final LeaderService leaderService;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");


    //https://www.webscale.com/engineering-education/how-to-generate-reports-in-a-spring-boot-app-leveraging-jaspersoft/
    private JasperPrint getJasperPrint(AppearanceRequest appearanceRequest, InputStream inputStream) throws JRException, IOException{
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        Map<String, Object> parameters = map(appearanceRequest);

        InputStream logoInput = getClass().getResourceAsStream("/static/images/logo.png");
        InputStream headingInput = getClass().getResourceAsStream("/static/images/coa_heading.png");

        parameters.put("logo",logoInput);
        parameters.put("heading",headingInput);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());
        return jasperPrint;
    }

    private Map<String, Object> map(AppearanceRequest appearanceRequest) {
        Map<String, Object> parameters = new HashMap<>();

       VisitorRequest visitorRequest = appearanceRequest.getVisitor();
        AddressRequest addressResponse = visitorRequest.getAddress();


        String dateOfTravel = getFormattedDateRange(appearanceRequest.getDateFrom(), appearanceRequest.getDateTo());


        String fullName = String.format("%s%s%s", visitorRequest.getFirstName(),
                visitorRequest.getMiddleInitial().equalsIgnoreCase("n/a") ? " " : visitorRequest.getMiddleInitial() + ". ",
                visitorRequest.getLastName());

        String fullAddress = String.format("%s%s, %s",addressResponse.getBarangay() == null ? "": String.format("%s, ",addressResponse.getBarangay().getName()),
                addressResponse.getMunicipality().getName(), addressResponse.getProvince().getName());

        String office = visitorRequest.getAgency() == null ? fullAddress : visitorRequest.getAgency().getName();

        LocalDate dateIssued = LocalDate.parse(appearanceRequest.getDateIssued(),dateTimeFormatter);

        parameters.put("courtesyTitle",visitorRequest.getCourtesyTitle().getTitle());
        parameters.put("fullName",fullName);
        parameters.put("position",visitorRequest.getPosition().getTitle());
        parameters.put("office",office);
        parameters.put("dateOfTravel",dateOfTravel);
        parameters.put("purpose", appearanceRequest.getPurpose().getDescription());
        parameters.put("reference",appearanceRequest.getReference());
        parameters.put("day",getDayWithOrdinal(dateIssued.getDayOfMonth()));
        parameters.put("month", String.format("%s%s",dateIssued.getMonth().toString().charAt(0),
                dateIssued.getMonth().toString().toLowerCase().substring(1)));

        parameters.put("year",String.valueOf(dateIssued.getYear()));
        parameters.put("lastName",visitorRequest.getLastName());

        LeaderResponse leaderResponse = leaderService.findByStatus(true)
                .map(leader ->modelMapper.map(leader,LeaderResponse.class)).orElseThrow(()->new ResourceNotFoundException("Leader","inCharge","true"));

        parameters.put("leaderName",leaderResponse.getName());
        parameters.put("leaderPosition",leaderResponse.getPosition());

        return parameters;


    }

    @Override
    public String generateSingleCertificate(Long templateNo, AppearanceRequest appearanceRequest) throws JRException, IOException {

       InputStream inputStream = getClass().getResourceAsStream("/static/certificates/CA_Template_1.jrxml");

        JasperPrint jasperPrint = getJasperPrint(appearanceRequest,inputStream);

        String fileName = "certificate.pdf";
        Path uploadPath = getUploadPath("pdf",jasperPrint,fileName);
        return getPdfFileLink(uploadPath.toString());
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
    private String getPdfFileLink(String uploadPath){
        return String.format("%s/%s",uploadPath,"certificate.pdf");
    }

    private String getFormattedDateRange(String strDateFrom, String strDateTo) {

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        DateTimeFormatter dateFromFormat = DateTimeFormatter.ofPattern("MMMM d");
        DateTimeFormatter dateToFormat = DateTimeFormatter.ofPattern("d, yyyy");

        LocalDate dateFrom = LocalDate.parse(strDateFrom,dateTimeFormatter);

        LocalDate dateTo = LocalDate.parse(strDateTo,dateTimeFormatter);

        String firstDate = "";
        String lastDate = "";

        String formattedDateRange = "";

        LocalDate[] dates = {dateFrom, dateTo};

        if (dateFrom.getYear() == dateTo.getYear()) {
            if (dateFrom.getMonth().equals(dateTo.getMonth())) {
                if (dateFrom.equals(dateTo)) {
                    formattedDateRange = dateTimeFormatter.format(dateFrom);
                } else {
                    firstDate = dateFromFormat.format(dates[0]);
                    lastDate = dateToFormat.format(dates[1]);
                    formattedDateRange = String.format("%s - %s", firstDate, lastDate);
                }
            } else {
                firstDate = dateFromFormat.format(dates[0]);
                lastDate = dateTimeFormatter.format(dates[1]);
                formattedDateRange = String.format("%s - %s", firstDate, lastDate);
            }
        } else {
            firstDate = dateTimeFormatter.format(dates[0]);
            lastDate = dateTimeFormatter.format(dates[1]);
            formattedDateRange = String.format("%s - %s", firstDate, lastDate);
        }

        return formattedDateRange;
    }

    private static String getDayWithOrdinal(int day){

        String suffix = "th";
        if(day%10 == 1 && day%100 != 11) suffix = "st";
        if(day%10 == 2 && day%100 != 12) suffix =  "nd";
        if(day%10 == 3 && day%100 != 13) suffix = "rd";

        return String.format("%d%s", day,suffix);
    }


}
