package com.tts.weatherapp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipCodeRepository extends JpaRepository<ZipCode, Long> {
    List<ZipCode> findByCode(String code);
}
