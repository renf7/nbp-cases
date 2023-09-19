package com.github.renf7.nbpcases.controller;

import com.github.renf7.nbpcases.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class CurrencyRateController {
    private final CurrencyRateService currencyRateService;

    @GetMapping("/{currencyCode}/{dateOfPublication}")
    public ResponseEntity<BigDecimal> getSellRate(@PathVariable String currencyCode,
                                                  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfPublication) {
        BigDecimal sellRate = currencyRateService.getSellRate(currencyCode, dateOfPublication);
        return ResponseEntity.ok(sellRate);
    }
}