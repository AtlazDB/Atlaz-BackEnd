package com.example.AtlazDB.repository;

import com.example.AtlazDB.enums.TypeFuel;
import com.example.AtlazDB.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FuelRepository extends JpaRepository<Fuel, Long> {

    List<Fuel> findByTipoCombustivel(TypeFuel typeFuel);

    List<Fuel> findByValorTotalGreaterThan(Double valorTotal);

    List<Fuel> findByKmAtualGreaterThan(Double kmAtual);



    @Query("SELECT a FROM Abastecimento a WHERE a.dataHora >= :inicio AND a.dataHora < :fim")
    List<Fuel> buscarPorPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
}