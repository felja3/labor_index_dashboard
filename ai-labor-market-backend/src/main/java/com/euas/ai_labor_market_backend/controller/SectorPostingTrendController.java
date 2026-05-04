package com.euas.ai_labor_market_backend.controller;

import com.euas.ai_labor_market_backend.dto.SectorPostingTrendDto;
import com.euas.ai_labor_market_backend.service.SectorPostingTrendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sector-postings")
public class SectorPostingTrendController {

    private final SectorPostingTrendService service;

    public SectorPostingTrendController(SectorPostingTrendService service) {
        this.service = service;
    }

    @GetMapping
    public List<SectorPostingTrendDto> getSectorPostingTrends(
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String postingType,
            @RequestParam(required = false) String country
    ) {
        return service.getSectorPostingTrends(sector, postingType, country);
    }
}