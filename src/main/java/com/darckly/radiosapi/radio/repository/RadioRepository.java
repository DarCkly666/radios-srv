package com.darckly.radiosapi.radio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darckly.radiosapi.radio.model.Radio;

@Repository
public interface RadioRepository extends JpaRepository<Radio, Long> {

}
