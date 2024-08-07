package com.coa.domain.report;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public enum ExportType {

    PDF("pdf", "application/pdf"),
    EXCEL("xlsx","application/octet-stream"),
    DOCX("docx","application/octet-stream");




    private final String extension;
    private final String contentType;


    public String getExtension() {
        return "." + extension;
    }

    public String getContentType() {
        return contentType;
    }
}
