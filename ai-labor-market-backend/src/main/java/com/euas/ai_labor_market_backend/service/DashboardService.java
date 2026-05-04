package com.euas.ai_labor_market_backend.service;

import com.euas.ai_labor_market_backend.dto.DashboardSummaryDto;
import com.euas.ai_labor_market_backend.dto.SectorIndexTrendDto;
import com.euas.ai_labor_market_backend.entity.SectorPostingTrend;
import com.euas.ai_labor_market_backend.repository.SectorPostingTrendRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final SectorPostingTrendRepository repository;

    public DashboardService(SectorPostingTrendRepository repository) {
        this.repository = repository;
    }

    public List<SectorIndexTrendDto> getSectorIndexTrends(
            String sector,
            String postingType,
            String country
    ) {
        boolean hasSector = sector != null && !sector.isBlank();
        boolean hasPostingType = postingType != null && !postingType.isBlank();
        boolean hasCountry = country != null && !country.isBlank();

        List<SectorPostingTrend> trends;

        if (hasSector && hasPostingType && hasCountry) {
            trends = repository.findBySectorNameAndPostingTypeAndJobCountryOrderByDateAsc(
                    sector,
                    postingType,
                    country
            );
        } else if (hasSector && hasPostingType) {
            trends = repository.findBySectorNameAndPostingTypeOrderByDateAsc(
                    sector,
                    postingType
            );
        } else if (hasSector && hasCountry) {
            trends = repository.findBySectorNameAndJobCountryOrderByDateAsc(
                    sector,
                    country
            );
        } else if (hasPostingType && hasCountry) {
            trends = repository.findByPostingTypeAndJobCountryOrderByDateAsc(
                    postingType,
                    country
            );
        } else if (hasSector) {
            trends = repository.findBySectorNameOrderByDateAsc(sector);
        } else if (hasPostingType) {
            trends = repository.findByPostingTypeOrderByDateAsc(postingType);
        } else if (hasCountry) {
            trends = repository.findByJobCountryOrderByDateAsc(country);
        } else {
            trends = repository.findAllByOrderByDateAsc();
        }

        return trends.stream()
                .map(this::toSectorIndexTrendDto)
                .toList();
    }

    public List<String> getSectorNames() {
        return repository.findDistinctSectorNames();
    }

    public List<String> getPostingTypes() {
        return repository.findDistinctPostingTypes();
    }

    public List<String> getCountries() {

        return repository.findDistinctJobCountries();

    }

    public DashboardSummaryDto getSummary() {
        return new DashboardSummaryDto(
                repository.count(),
                repository.countDistinctSectorNames(),
                repository.countDistinctPostingTypes(),
                repository.findEarliestDate(),
                repository.findLatestDate()
        );
    }

    private SectorIndexTrendDto toSectorIndexTrendDto(SectorPostingTrend trend) {
        return new SectorIndexTrendDto(
                trend.getDate(),
                trend.getSectorName(),
                trend.getPostingType(),
                trend.getJobCountry(),
                trend.getIndexValue()
        );
    }
}