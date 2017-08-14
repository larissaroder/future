package br.com.db1.service;

import br.com.db1.model.Rate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RateService {

    private static final String URL = "https://bitpay.com/api/rates";
    public List<Rate> getRates() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Rate>> rateResponse =
                restTemplate.exchange(URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Rate>>() {
                        });
        return rateResponse.getBody();
    }
}
