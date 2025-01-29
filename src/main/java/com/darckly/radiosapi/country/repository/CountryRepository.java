package com.darckly.radiosapi.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darckly.radiosapi.country.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
