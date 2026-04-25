package com.example.AtlazDB.repository;

import com.example.AtlazDB.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViaturaRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByPrefixo(String prefixo);
}
