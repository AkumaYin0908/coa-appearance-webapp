package com.coa.domain.report.dynamic;

import lombok.Data;

import java.util.List;

@Data
public class DynamicReportProperties {

    private List<String> columnHeaders;
    private List<Integer> indexesOfColumnTypeNumber;
    private List<List<String>> rows;
    private List<String> summary;


}
