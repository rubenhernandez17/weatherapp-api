package com.tts.weatherapp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final ZipCodeRepository zipCodeRepository;

    public WeatherService(ZipCodeRepository zipCodeRepository) {
        this.zipCodeRepository = zipCodeRepository;
    }

    @Value("${api_key}")
    private String apiKey;

    public Response getForecast(String zipCode) {
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + zipCode
                + "&units=imperial&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        Response response;
        try {
            response = restTemplate.getForObject(url, Response.class);
        } catch (HttpClientErrorException ex) {
            response = new Response();
            response.setName("error");
            return response;
        }
        ZipCode newZipCode = new ZipCode(null, zipCode, LocalDateTime.now());
        zipCodeRepository.save(newZipCode);
        return response;
    }

    public List<ZipCode> getRecentSearches() {
        Sort sort = Sort.by(new Order(Direction.DESC, "timestamp"));
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        return zipCodeRepository.findAll(pageRequest).getContent();
    }

    public List<ZipCode> searchZipCodes(String query) {
        return null;
    }

}
