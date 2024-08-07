package com.coa.domain.report.dynamic;

import com.coa.domain.report.ExportType;
import com.coa.domain.report.Report;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.InputStream;
import java.util.Collection;



public class DynamicReport extends Report {
    private InputStream inputStream;
    private String title;
    private ExportType exportType;
    private HttpServletResponse response;
    private DynamicReportProperties dynamicReportProperties;

    public DynamicReport(InputStream inputStream, String title, Collection<?> beanCollection, ExportType exportType, HttpServletResponse response, InputStream inputStream1, String title1, ExportType exportType1, HttpServletResponse response1, DynamicReportProperties dynamicReportProperties) {
        super(inputStream, title, null, exportType, response);
        this.dynamicReportProperties=dynamicReportProperties;
    }

    public DynamicReportProperties getDynamicReportProperties() {
        return dynamicReportProperties;
    }
}
