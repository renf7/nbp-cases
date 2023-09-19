package com.github.renf7.nbpcases.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExchangeRatesSeries {
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;
}
