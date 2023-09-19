package com.github.renf7.nbpcases.service;

import com.github.renf7.nbpcases.entity.CurrencyRate;
import com.github.renf7.nbpcases.repository.CurrencyRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateServiceTest {

    @Mock
    private CurrencyRateRepository currencyRateRepository;

    @Mock
    private NbpService nbpService;

    @InjectMocks
    private CurrencyRateService currencyRateService;

    @Test
    public void getSellRateFromDatabaseTest() {
        String currencyCode = "USD";
        LocalDate dateOfPublication = LocalDate.now();
        BigDecimal expectedRate = BigDecimal.valueOf(3.75);

        CurrencyRate mockRate = new CurrencyRate();
        mockRate.setCurrencyCode(currencyCode);
        mockRate.setDateOfPublication(dateOfPublication);
        mockRate.setSellRate(expectedRate);

        when(currencyRateRepository.findByCurrencyCodeAndDateOfPublication(currencyCode, dateOfPublication))
                .thenReturn(Optional.of(mockRate));

        BigDecimal result = currencyRateService.getSellRate(currencyCode, dateOfPublication);

        assertEquals(expectedRate, result);
        verify(nbpService, times(0)).fetchSellRateFromNbpApi(anyString(), any(LocalDate.class));
    }

    @Test
    public void getSellRateFromNbpApiTest() {
        String currencyCode = "USD";
        LocalDate dateOfPublication = LocalDate.now();
        BigDecimal expectedRate = BigDecimal.valueOf(3.75);

        when(currencyRateRepository.findByCurrencyCodeAndDateOfPublication(currencyCode, dateOfPublication))
                .thenReturn(Optional.empty());
        when(nbpService.fetchSellRateFromNbpApi(currencyCode, dateOfPublication)).thenReturn(Mono.just(expectedRate));

        BigDecimal result = currencyRateService.getSellRate(currencyCode, dateOfPublication);

        assertEquals(expectedRate, result);
        verify(nbpService, times(1)).fetchSellRateFromNbpApi(currencyCode, dateOfPublication);
    }

}