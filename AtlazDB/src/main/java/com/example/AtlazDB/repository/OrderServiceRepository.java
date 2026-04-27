package com.example.AtlazDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.AtlazDB.model.OrderService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderServiceRepository extends JpaRepository<OrderService, Long> {

    @Query("SELECT o FROM OrdemServico o WHERE o.dataSaida >= :inicio AND o.dataSaida < :fim")
    List<OrderService> searchByPeriod(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
