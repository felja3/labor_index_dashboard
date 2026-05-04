package com.euas.ai_labor_market_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PercentageChangeDto {

    private String sectorName;
    private String country;
    private String comparison;

    private LocalDate latestDate;
    private BigDecimal latestValue;

    private LocalDate comparisonDate;
    private BigDecimal comparisonValue;

    private BigDecimal percentageChange;

    public PercentageChangeDto(
            String sectorName,
            String country,
            String comparison,
            LocalDate latestDate,
            BigDecimal latestValue,
            LocalDate comparisonDate,
            BigDecimal comparisonValue,
            BigDecimal percentageChange
    ) {
        this.sectorName = sectorName;
        this.country = country;
        this.comparison = comparison;
        this.latestDate = latestDate;
        this.latestValue = latestValue;
        this.comparisonDate = comparisonDate;
        this.comparisonValue = comparisonValue;
        this.percentageChange = percentageChange;
    }

    public String getSectorName() {
        return sectorName;
    }

    public String getCountry() {
        return country;
    }

    public String getComparison() {
        return comparison;
    }

    public LocalDate getLatestDate() {
        return latestDate;
    }

    public BigDecimal getLatestValue() {
        return latestValue;
    }

    public LocalDate getComparisonDate() {
        return comparisonDate;
    }

    public BigDecimal getComparisonValue() {
        return comparisonValue;
    }

    public BigDecimal getPercentageChange() {
        return percentageChange;
    }
}