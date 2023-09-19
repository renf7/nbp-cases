package com.github.renf7.nbpcases.repository;

import com.github.renf7.nbpcases.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findByCurrencyCodeAndDateOfPublication(String currencyCode, LocalDate dateOfPublication);
}