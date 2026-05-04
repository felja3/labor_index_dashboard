package com.euas.ai_labor_market_backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sector_posting_trends")
public class SectorPostingTrend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "job_country", nullable = false)
    private String jobCountry;

    @Column(name = "index_value", nullable = false)
    private BigDecimal indexValue;

    @Column(name = "posting_type", nullable = false)
    private String postingType;

    @Column(name = "sector_name", nullable = false)
    private String sectorName;

    public SectorPostingTrend() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setJobCountry(String jobCountry) {
        this.jobCountry = jobCountry;
    }

    public void setIndexValue(BigDecimal indexValue) {
        this.indexValue = indexValue;
    }

    public void setPostingType(String postingType) {
        this.postingType = postingType;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }
}