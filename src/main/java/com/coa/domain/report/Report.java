package com.coa.domain.report;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static com.coa.domain.report.ExportType.PDF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    public static final String ATTACHMENT_FILENAME = "attachment;filename=";

    private InputStream inputStream;
    private String title;
    private Collection<?> beanCollection;
    private ExportType exportType;
    private HttpServletResponse response;



    public void exportViaJasperReport(JasperPrint jasperPrint) throws JRException, IOException {
        if(exportType.equals(PDF)){
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            setHeaderAndContentType(response,exportType);
            exporter.exportReport();
        }
    }
    private void setHeaderAndContentType(HttpServletResponse response, ExportType exportType) throws UnsupportedEncodingException {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,getHeaderValue(exportType));
        response.setContentType(exportType.getContentType());
    }

    private String getHeaderValue(ExportType exportType) throws UnsupportedEncodingException{
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTimeNow = LocalDateTime.now().format(dateTimeFormatter);
        String fileName = URLEncoder.encode(title.replaceAll("[^\\w-]+","_") + "_" + dateTimeNow,"UTF-8");
        return ATTACHMENT_FILENAME + fileName + exportType.getExtension();
    }





}
