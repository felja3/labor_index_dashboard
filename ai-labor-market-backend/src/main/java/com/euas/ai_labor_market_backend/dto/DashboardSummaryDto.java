package com.euas.ai_labor_market_backend.dto;

import java.time.LocalDate;

public class DashboardSummaryDto {

    private long totalRows;
    private long sectorCount;
    private long postingTypeCount;
    private LocalDate earliestDate;
    private LocalDate latestDate;

    public DashboardSummaryDto(
            long totalRows,
            long sectorCount,
            long postingTypeCount,
            LocalDate earliestDate,
            LocalDate latestDate
    ) {
        this.totalRows = totalRows;
        this.sectorCount = sectorCount;
        this.postingTypeCount = postingTypeCount;
        this.earliestDate = earliestDate;
        this.latestDate = latestDate;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public long getSectorCount() {
        return sectorCount;
    }

    public long getPostingTypeCount() {
        return postingTypeCount;
    }

    public LocalDate getEarliestDate() {
        return earliestDate;
    }

    public LocalDate getLatestDate() {
        return latestDate;
    }
}