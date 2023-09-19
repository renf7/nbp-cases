package com.github.renf7.nbpcases.service;

import com.github.renf7.nbpcases.entity.CurrencyRate;
import com.github.renf7.nbpcases.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {

    private final CurrencyRateRepository currencyRateRepository;
    private final NbpService nbpService;

    public BigDecimal getSellRate(String currencyCode, LocalDate dateOfPublication) {
        // 1. Check in the local database first
        Optional<CurrencyRate> rate = currencyRateRepository.findByCurrencyCodeAndDateOfPublication(currencyCode, dateOfPublication);

        if(rate.isPresent()) {
            return rate.get().getSellRate();
        } else {
            // 2. If not found, fetch from NBP API and save in local DB
            BigDecimal fetchedRate = nbpService.fetchSellRateFromNbpApi(currencyCode, dateOfPublication)
                    .block();

            CurrencyRate newRate = new CurrencyRate();
            newRate.setCurrencyCode(currencyCode);
            newRate.setDateOfPublication(dateOfPublication);
            newRate.setSellRate(fetchedRate);

            currencyRateRepository.save(newRate);

            return fetchedRate;
        }
    }

}