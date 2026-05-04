package com.euas.ai_labor_market_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SectorIndexTrendDto {

    private LocalDate date;
    private String sectorName;
    private String postingType;
    private String jobCountry;
    private BigDecimal indexValue;

    public SectorIndexTrendDto(
            LocalDate date,
            String sectorName,
            String postingType,
            String jobCountry,
            BigDecimal indexValue
    ) {
        this.date = date;
        this.sectorName = sectorName;
        this.postingType = postingType;
        this.jobCountry = jobCountry;
        this.indexValue = indexValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getSectorName() {
        return sectorName;
    }

    public String getPostingType() {
        return postingType;
    }

    public String getJobCountry() {
        return jobCountry;
    }

    public BigDecimal getIndexValue() {
        return indexValue;
    }
}