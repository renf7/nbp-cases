package com.github.renf7.nbpcases.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class CurrencyCalculatorServiceTest {

    @Mock
    private CurrencyRateService currencyRateService;

    @InjectMocks
    private CurrencyCalculatorService currencyCalculatorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateTotalCostInPLN() {
        List<String> currencyCodes = Arrays.asList("USD", "EUR");
        LocalDate date = LocalDate.parse("2023-09-19");

        given(currencyRateService.getSellRate("USD", date)).willReturn(BigDecimal.valueOf(3.8));
        given(currencyRateService.getSellRate("EUR", date)).willReturn(BigDecimal.valueOf(4.3));

        BigDecimal totalCostInPLN = currencyCalculatorService.calculateTotalCostInPLN(currencyCodes, date);

        BigDecimal expectedTotal = BigDecimal.valueOf(4.3).add(BigDecimal.valueOf(3.8));
        assertThat(totalCostInPLN).isEqualByComparingTo(expectedTotal);
    }
}