package com.github.renf7.nbpcases.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CurrencyListRequestDTO {
    private List<String> currencies;
    private LocalDate date;
}