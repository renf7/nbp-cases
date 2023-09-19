package com.github.renf7.nbpcases.controller;

import com.github.renf7.nbpcases.dto.CurrencyListRequestDTO;
import com.github.renf7.nbpcases.dto.TotalCostResponseDTO;
import com.github.renf7.nbpcases.service.CurrencyCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency-calculator")
@RequiredArgsConstructor
public class CurrencyCalculatorController {

    private final CurrencyCalculatorService currencyCalculatorService;

    @PostMapping("/calculate-total-cost")
    public TotalCostResponseDTO calculateTotalCost(@RequestBody CurrencyListRequestDTO request) {
        BigDecimal totalCost = currencyCalculatorService.calculateTotalCostInPLN(request.getCurrencies(), request.getDate());
        return new TotalCostResponseDTO(totalCost);
    }
}