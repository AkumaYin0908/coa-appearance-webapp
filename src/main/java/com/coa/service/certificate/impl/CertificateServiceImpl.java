package com.coa.service.certificate.impl;

import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.response.AgencyResponse;
import com.coa.payload.response.AppearanceResponse;
import com.coa.payload.response.VisitorResponse;
import com.coa.payload.response.address.AddressResponse;
import com.coa.service.certificate.CertificateService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
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


@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {


    private final ModelMapper modelMapper;


    //https://www.webscale.com/engineering-education/how-to-generate-reports-in-a-spring-boot-app-leveraging-jaspersoft/
    private JasperPrint getJasperPrint(AppearanceResponse appearanceResponse, String resourceLocation) throws JRException, IOException{
        File file = ResourceUtils.getFile(resourceLocation);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = map(appearanceResponse);

        InputStream logoInput = getClass().getResourceAsStream("static/images/logo.png");
        InputStream headingInput = getClass().getResourceAsStream("static/images/name heading.png");

        parameters.put("logo",logoInput);
        parameters.put("heading",headingInput);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());
        return jasperPrint;
    }

    private Map<String, Object> map(AppearanceResponse appearanceResponse) {
        Map<String, Object> parameters = new HashMap<>();

        VisitorResponse visitorResponse = appearanceResponse.getVisitor();
        AddressResponse addressResponse = visitorResponse.getAddress();


        String dateOfTravel = getFormattedDateRange(appearanceResponse.getDateFrom(), appearanceResponse.getDateTo());


        String fullName = String.format("%s%s%s", visitorResponse.getFirstName(),
                visitorResponse.getMiddleInitial().equalsIgnoreCase("n/a") ? " " : visitorResponse.getMiddleInitial() + ". ",
                visitorResponse.getLastName());

        String fullAddress = String.format("%s%s, %s",addressResponse.getBarangay() == null ? "": String.format("%s, ",addressResponse.getBarangay().getName()),
                addressResponse.getMunicipality().getName(), addressResponse.getProvince().getName());

        String office = visitorResponse.getAgency() == null ? fullAddress : visitorResponse.getAgency().getName();

        LocalDate dateIssued = LocalDate.parse(appearanceResponse.getDateIssued());

        parameters.put("courtesyTitle",visitorResponse.getCourtesyTitle().getTitle());
        parameters.put("fullName",fullName);
        parameters.put("position",visitorResponse.getPosition().getTitle());
        parameters.put("office",office);
        parameters.put("dateOfTravel",dateOfTravel);
        parameters.put("purpose", appearanceResponse.getPurpose().getDescription());
        parameters.put("reference",appearanceResponse.getReference());
        parameters.put("day",getDayWithOrdinal(dateIssued.getDayOfMonth()));
        parameters.put("month", String.format("%s%s",dateIssued.getMonth().toString().charAt(0),
                dateIssued.getMonth().toString().toLowerCase().substring(1)));

        parameters.put("year",dateIssued.getYear());
        parameters.put("lastName",visitorResponse.getLastName());

        return parameters;


    }

    @Override
    public String generateSingleCertificate(AppearanceRequest appearanceRequest,int templateNo) throws JRException, IOException {
        String resourceLocation = String.format("classpath: /static/certificates/CA_Template_%d.jrxml",templateNo);

        AppearanceResponse appearanceResponse = modelMapper.map(appearanceRequest,AppearanceResponse.class);
        JasperPrint jasperPrint = getJasperPrint(appearanceResponse,resourceLocation);

        String fileName = "certificate.pdf";
        Path uploadPath = getUploadPath("pdf",jasperPrint,fileName);
        return String.format("%s/%s",uploadPath.toString(),fileName);
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

    private String getFormattedDateRange(String strDateFrom, String strDateTo) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        DateTimeFormatter dateFromFormat = DateTimeFormatter.ofPattern("MMMM d");
        DateTimeFormatter dateToFormat = DateTimeFormatter.ofPattern("d, yyyy");

        LocalDate dateFrom = LocalDate.parse(strDateFrom);
        System.out.println(dateFrom);
        LocalDate dateTo = LocalDate.parse(strDateTo);
        System.out.println(dateTo);
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
