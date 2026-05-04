package com.euas.ai_labor_market_backend.controller;

import com.euas.ai_labor_market_backend.dto.DashboardSummaryDto;
import com.euas.ai_labor_market_backend.dto.PercentageChangeDto;
import com.euas.ai_labor_market_backend.dto.SectorIndexTrendDto;
import com.euas.ai_labor_market_backend.service.DashboardService;
import com.euas.ai_labor_market_backend.service.PercentageChangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;
    private final PercentageChangeService percentageChangeService;

    public DashboardController(
            DashboardService service,
            PercentageChangeService percentageChangeService
    ) {
        this.service = service;
        this.percentageChangeService = percentageChangeService;
    }

    @GetMapping("/sector-index-trends")
    public List<SectorIndexTrendDto> getSectorIndexTrends(
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String postingType,
            @RequestParam(required = false) String country
    ) {
        return service.getSectorIndexTrends(sector, postingType, country);
    }

    @GetMapping("/sectors")
    public List<String> getSectors() {
        return service.getSectorNames();
    }

    @GetMapping("/posting-types")
    public List<String> getPostingTypes() {
        return service.getPostingTypes();
    }

    @GetMapping("/countries")
    public List<String> getCountries() {
        return service.getCountries();
    }

    @GetMapping("/summary")
    public DashboardSummaryDto getSummary() {
        return service.getSummary();
    }

    @GetMapping("/percentage-change")
    public List<PercentageChangeDto> getPercentageChange(
            @RequestParam(defaultValue = "month") String comparison
    ) {
        return percentageChangeService.getPercentageChange(comparison);
    }
}