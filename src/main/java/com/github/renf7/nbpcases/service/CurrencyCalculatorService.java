package com.github.renf7.nbpcases.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyCalculatorService {
    private CurrencyRateService currencyRateService;

    // This method should fetch the middle exchange rate for each currency and sum the results.
    public BigDecimal calculateTotalCostInPLN(List<String> currencies, LocalDate date) {
        // For each currency in the list, fetch its middle exchange rate and accumulate the total.

        BigDecimal totalCost = BigDecimal.ZERO;
        for (String currency : currencies) {
            BigDecimal middleRate = currencyRateService.getSellRate(currency, date);
            totalCost = totalCost.add(middleRate);
        }
        return totalCost;
    }

}