package com.github.renf7.nbpcases.dto;

import lombok.Data;

import java.util.List;

@Data
public
class NbpResponse {
    private List<Rate> rates;
}