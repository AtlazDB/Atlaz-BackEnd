package com.example.AtlazDB.controller;

import java.util.List;

import com.example.AtlazDB.model.City;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AtlazDB.dto.CityRequestDTO;
import com.example.AtlazDB.dto.CityResponseDTO;
import com.example.AtlazDB.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CityResponseDTO>> list() {
        List<City> list = service.ListAll();
        
        List<CityResponseDTO> dtoList = list.stream()
                .map(CityResponseDTO::new)
                .toList();
                
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponseDTO> searchById(@PathVariable Long id) {
        return service.searchById(id)
                .map(CityResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CityResponseDTO> create(@RequestBody CityRequestDTO dto) {
        City saved = service.save(dto);
        return ResponseEntity.ok(new CityResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponseDTO> update(@PathVariable Long id, @RequestBody CityRequestDTO dto) {
        City updated = service.update(id, dto);
        return ResponseEntity.ok(new CityResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
