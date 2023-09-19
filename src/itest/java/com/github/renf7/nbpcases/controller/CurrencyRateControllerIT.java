package com.github.renf7.nbpcases.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyRateControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testEndpoint() {
        // Construct the URL
        String url = "http://localhost:" + port + "/api/rates/USD/2023-09-19";

        // Make the REST call
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Assert the results
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}