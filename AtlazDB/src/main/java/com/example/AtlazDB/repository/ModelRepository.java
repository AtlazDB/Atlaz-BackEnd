package com.example.AtlazDB.repository;

import com.example.AtlazDB.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
