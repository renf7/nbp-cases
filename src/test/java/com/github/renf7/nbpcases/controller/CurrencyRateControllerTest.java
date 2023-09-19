package com.github.renf7.nbpcases.controller;

import com.github.renf7.nbpcases.service.CurrencyRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyRateService currencyRateService;

    @BeforeEach
    public void setup() {
        when(currencyRateService.getSellRate("USD", LocalDate.of(2023, 9, 19)))
                .thenReturn(new BigDecimal("4.50"));
    }

    @Test
    public void testGetSellRate() throws Exception {
        mockMvc.perform(get("/api/rates/USD/2023-09-19"))
                .andExpect(status().isOk())
                .andExpect(content().string("4.50"));
    }
}