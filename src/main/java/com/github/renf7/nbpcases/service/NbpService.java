package com.github.renf7.nbpcases.service;

import com.github.renf7.nbpcases.dto.ExchangeRatesSeries;
import com.github.renf7.nbpcases.dto.NbpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NbpService {

    private final WebClient.Builder webClientBuilder;
    @Value("${nbp.url:http://api.nbp.pl/api}")
    private String nbpUrl;
    @Value("${nbp.rates.url:/exchangerates/rates/c/{currencyCode}/{date}/}")
    private String ntpRatesUrl;
    private WebClient webClient;

    @PostConstruct
    public void postConstruct() {
        this.webClient = webClientBuilder.baseUrl(nbpUrl).build();
    }

    public Mono<BigDecimal> fetchSellRateFromNbpApi(String currency, LocalDate date) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(ntpRatesUrl).build(currency, date.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .retrieve()
                .bodyToMono(ExchangeRatesSeries.class)
                .map(series -> {
                    if (!series.getRates().isEmpty()) {
                        return series.getRates().get(0).getAsk();
                    }
                    return null;
                });
    }
}



