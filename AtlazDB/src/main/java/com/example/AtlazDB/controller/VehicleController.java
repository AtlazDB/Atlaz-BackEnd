package com.example.AtlazDB.controller;

import java.util.List;

import com.example.AtlazDB.model.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AtlazDB.dto.VehicleRequestDTO;
import com.example.AtlazDB.dto.VehicleResponseDTO;
import com.example.AtlazDB.service.VehicleService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> list() {
        List<Vehicle> list = service.listAll();
        
        List<VehicleResponseDTO> dtoList = list.stream()
                .map(VehicleResponseDTO::new)
                .toList();
                
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> searchById(@PathVariable Long id) {
        return service.searchById(id)
                .map(VehicleResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(@RequestBody VehicleRequestDTO dto) {
        Vehicle saved = service.save(dto);
        return ResponseEntity.ok(new VehicleResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(@PathVariable Long id, @RequestBody VehicleRequestDTO dto) {
        Vehicle updated = service.update(id, dto);
        return ResponseEntity.ok(new VehicleResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
