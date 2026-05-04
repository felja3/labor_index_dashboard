package com.euas.ai_labor_market_backend.repository;

import com.euas.ai_labor_market_backend.entity.SectorPostingTrend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SectorPostingTrendRepository extends JpaRepository<SectorPostingTrend, Long> {

    String TOTAL_POSTINGS = "total postings";

    List<SectorPostingTrend> findAllByOrderByDateAsc();

    List<SectorPostingTrend> findBySectorNameOrderByDateAsc(String sectorName);

    List<SectorPostingTrend> findByPostingTypeOrderByDateAsc(String postingType);

    List<SectorPostingTrend> findByJobCountryOrderByDateAsc(String jobCountry);

    List<SectorPostingTrend> findBySectorNameAndPostingTypeOrderByDateAsc(
            String sectorName,
            String postingType
    );

    List<SectorPostingTrend> findBySectorNameAndJobCountryOrderByDateAsc(
            String sectorName,
            String jobCountry
    );

    List<SectorPostingTrend> findByPostingTypeAndJobCountryOrderByDateAsc(
            String postingType,
            String jobCountry
    );

    List<SectorPostingTrend> findBySectorNameAndPostingTypeAndJobCountryOrderByDateAsc(
            String sectorName,
            String postingType,
            String jobCountry
    );

    @Query("SELECT DISTINCT s.sectorName FROM SectorPostingTrend s ORDER BY s.sectorName")
    List<String> findDistinctSectorNames();

    @Query("SELECT DISTINCT s.postingType FROM SectorPostingTrend s ORDER BY s.postingType")
    List<String> findDistinctPostingTypes();

    @Query("SELECT DISTINCT s.jobCountry FROM SectorPostingTrend s ORDER BY s.jobCountry")
    List<String> findDistinctJobCountries();

    @Query("SELECT MIN(s.date) FROM SectorPostingTrend s")
    LocalDate findEarliestDate();

    @Query("SELECT MAX(s.date) FROM SectorPostingTrend s")
    LocalDate findLatestDate();

    @Query("SELECT COUNT(DISTINCT s.sectorName) FROM SectorPostingTrend s")
    long countDistinctSectorNames();

    @Query("SELECT COUNT(DISTINCT s.postingType) FROM SectorPostingTrend s")
    long countDistinctPostingTypes();

    SectorPostingTrend findTopByJobCountryAndSectorNameAndPostingTypeOrderByDateDesc(
            String jobCountry,
            String sectorName,
            String postingType
    );

    SectorPostingTrend findTopByJobCountryAndSectorNameAndPostingTypeAndDateLessThanEqualOrderByDateDesc(
            String jobCountry,
            String sectorName,
            String postingType,
            LocalDate date
    );

    SectorPostingTrend findTopByJobCountryAndSectorNameAndPostingTypeOrderByDateAsc(
            String jobCountry,
            String sectorName,
            String postingType
    );
    SectorPostingTrend findTopByJobCountryAndSectorNameAndPostingTypeOrderByIndexValueDesc(
            String jobCountry,
            String sectorName,
            String postingType
    );
}