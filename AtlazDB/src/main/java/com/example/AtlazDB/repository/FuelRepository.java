package com.example.AtlazDB.repository;

import com.example.AtlazDB.enums.TypeFuel;
import com.example.AtlazDB.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FuelRepository extends JpaRepository<Fuel, Long> {

    List<Fuel> findByTypeFuel(TypeFuel typeFuel);

    List<Fuel> findByTotalValueGreaterThan(Double totalValue);

    List<Fuel> findByCurrentKmGreaterThan(Double currentKm);



    @Query("SELECT a FROM Abastecimento a WHERE a.dataHora >= :inicio AND a.dataHora < :fim")
    List<Fuel> searchByPeriod(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}