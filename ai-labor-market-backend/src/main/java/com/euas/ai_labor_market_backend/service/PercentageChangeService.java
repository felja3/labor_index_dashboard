package com.euas.ai_labor_market_backend.service;

import com.euas.ai_labor_market_backend.dto.PercentageChangeDto;
import com.euas.ai_labor_market_backend.entity.SectorPostingTrend;
import com.euas.ai_labor_market_backend.repository.SectorPostingTrendRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PercentageChangeService {

    private static final String TOTAL_POSTINGS = "total postings";

    private final SectorPostingTrendRepository repository;

    public PercentageChangeService(SectorPostingTrendRepository repository) {
        this.repository = repository;
    }

    public List<PercentageChangeDto> getPercentageChange(String comparison) {
        String selectedComparison = normalizeComparison(comparison);

        List<String> countries = repository.findDistinctJobCountries();
        List<String> sectors = repository.findDistinctSectorNames();
        List<PercentageChangeDto> results = new ArrayList<>();

        for (String country : countries) {
            for (String sector : sectors) {
                PercentageChangeDto dto = calculateForCountryAndSector(
                        country,
                        sector,
                        selectedComparison
                );

                if (dto != null) {
                    results.add(dto);
                }
            }
        }

        return results;
    }

    private PercentageChangeDto calculateForCountryAndSector(
            String country,
            String sector,
            String comparison
    ) {
        SectorPostingTrend latestRecord = repository
                .findTopByJobCountryAndSectorNameAndPostingTypeOrderByDateDesc(
                        country,
                        sector,
                        TOTAL_POSTINGS
                );

        if (latestRecord == null || latestRecord.getIndexValue() == null) {
            return null;
        }

        SectorPostingTrend comparisonRecord = findComparisonRecord(
                country,
                sector,
                comparison,
                latestRecord.getDate()
        );

        if (comparisonRecord == null || comparisonRecord.getIndexValue() == null) {
            return null;
        }

        BigDecimal comparisonValue = comparisonRecord.getIndexValue();

        if (comparisonValue.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        BigDecimal latestValue = latestRecord.getIndexValue();
        BigDecimal percentageChange = calculatePercentageChange(latestValue, comparisonValue);

        return new PercentageChangeDto(
                sector,
                country,
                comparison,
                latestRecord.getDate(),
                latestValue,
                comparisonRecord.getDate(),
                comparisonValue,
                percentageChange
        );
    }

    private SectorPostingTrend findComparisonRecord(
            String country,
            String sector,
            String comparison,
            LocalDate latestDate
    ) {
        return switch (comparison) {
            case "month" -> repository
                    .findTopByJobCountryAndSectorNameAndPostingTypeAndDateLessThanEqualOrderByDateDesc(
                            country,
                            sector,
                            TOTAL_POSTINGS,
                            latestDate.minusMonths(1)
                    );
            case "sixMonths" -> repository
                    .findTopByJobCountryAndSectorNameAndPostingTypeAndDateLessThanEqualOrderByDateDesc(
                            country,
                            sector,
                            TOTAL_POSTINGS,
                            latestDate.minusMonths(6)
                    );
            case "year" -> repository
                    .findTopByJobCountryAndSectorNameAndPostingTypeAndDateLessThanEqualOrderByDateDesc(
                            country,
                            sector,
                            TOTAL_POSTINGS,
                            latestDate.minusYears(1)
                    );
            case "baseline" -> repository
                    .findTopByJobCountryAndSectorNameAndPostingTypeOrderByDateAsc(
                            country,
                            sector,
                            TOTAL_POSTINGS
                    );
            case "peak" -> repository
                    .findTopByJobCountryAndSectorNameAndPostingTypeOrderByIndexValueDesc(
                            country,
                            sector,
                            TOTAL_POSTINGS
                    );
            default -> repository
                    .findTopByJobCountryAndSectorNameAndPostingTypeAndDateLessThanEqualOrderByDateDesc(
                            country,
                            sector,
                            TOTAL_POSTINGS,
                            latestDate.minusMonths(1)
                    );
        };
    }

    private BigDecimal calculatePercentageChange(BigDecimal latestValue, BigDecimal comparisonValue) {
        return latestValue
                .subtract(comparisonValue)
                .divide(comparisonValue, 6, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private String normalizeComparison(String comparison) {
        if (comparison == null || comparison.isBlank()) {
            return "month";
        }

        return switch (comparison) {
            case "month", "sixMonths", "year", "baseline", "peak" -> comparison;
            default -> "month";
        };
    }
}
