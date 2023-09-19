package com.github.renf7.nbpcases.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.renf7.nbpcases.dto.CurrencyListRequestDTO;
import com.github.renf7.nbpcases.service.CurrencyCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyCalculatorController.class)
public class CurrencyCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyCalculatorService currencyCalculatorService;

    @Test
    public void testCalculateTotalCost() throws Exception {
        List<String> currencyCodes = Arrays.asList("USD", "EUR");
        LocalDate date = LocalDate.parse("2023-09-19");

        CurrencyListRequestDTO requestDTO = new CurrencyListRequestDTO();
        requestDTO.setCurrencies(currencyCodes);
        requestDTO.setDate(date);

        BigDecimal expectedTotal = new BigDecimal("100.00");
        given(currencyCalculatorService.calculateTotalCostInPLN(currencyCodes, date)).willReturn(expectedTotal);


        ObjectMapper objectMapper = new ObjectMapper();
        // Deals with error:
        // Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handlin
        objectMapper.registerModule(new JavaTimeModule());

        String requestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(post("/api/currency-calculator/calculate-total-cost")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCostInPLN").value(expectedTotal.doubleValue()));
    }
}