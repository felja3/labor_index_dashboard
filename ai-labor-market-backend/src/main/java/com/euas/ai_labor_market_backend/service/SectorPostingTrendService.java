package com.euas.ai_labor_market_backend.service;

import com.euas.ai_labor_market_backend.dto.SectorPostingTrendDto;
import com.euas.ai_labor_market_backend.entity.SectorPostingTrend;
import com.euas.ai_labor_market_backend.repository.SectorPostingTrendRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorPostingTrendService {

    private final SectorPostingTrendRepository repository;

    public SectorPostingTrendService(SectorPostingTrendRepository repository) {
        this.repository = repository;
    }

    public List<SectorPostingTrendDto> getSectorPostingTrends(
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
                .map(this::toDto)
                .toList();
    }

    private SectorPostingTrendDto toDto(SectorPostingTrend trend) {
        return new SectorPostingTrendDto(
                trend.getId(),
                trend.getDate(),
                trend.getJobCountry(),
                trend.getIndexValue(),
                trend.getPostingType(),
                trend.getSectorName()
        );
    }
}