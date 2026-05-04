package com.euas.ai_labor_market_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SectorPostingTrendDto {

    private Long id;
    private LocalDate date;
    private String jobCountry;
    private BigDecimal indexValue;
    private String postingType;
    private String sectorName;

    public SectorPostingTrendDto(
            Long id,
            LocalDate date,
            String jobCountry,
            BigDecimal indexValue,
            String postingType,
            String sectorName
    ) {
        this.id = id;
        this.date = date;
        this.jobCountry = jobCountry;
        this.indexValue = indexValue;
        this.postingType = postingType;
        this.sectorName = sectorName;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getJobCountry() {
        return jobCountry;
    }

    public BigDecimal getIndexValue() {
        return indexValue;
    }

    public String getPostingType() {
        return postingType;
    }

    public String getSectorName() {
        return sectorName;
    }
}