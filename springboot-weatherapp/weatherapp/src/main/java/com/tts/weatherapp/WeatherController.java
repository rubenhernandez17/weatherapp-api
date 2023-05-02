package com.tts.weatherapp;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("recentSearches", weatherService.getRecentSearches());
        return "index";
    }

    @PostMapping("/")
    public String postIndex(Request request, Model model) {
        Response data = weatherService.getForecast(request.getZipCode());
        model.addAttribute("data", data);
        model.addAttribute("request", request);
        model.addAttribute("recentSearches", weatherService.getRecentSearches());
        return "index";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam(name = "q") String query, Model model) {
        List<ZipCode> searchResults = weatherService.searchZipCodes(query);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("request", new Request());
        model.addAttribute("recentSearches", weatherService.getRecentSearches());
        return "search";
    }
    
    @GetMapping("/search/results")
    public String getSearchResults(@RequestParam(name = "q") String query, Model model) {
        List<ZipCode> searchResults = weatherService.searchZipCodes(query);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("request", new Request());
        model.addAttribute("recentSearches", weatherService.getRecentSearches());
        return "searchResults";
    }

}
