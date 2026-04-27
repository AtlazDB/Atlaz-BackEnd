package com.example.AtlazDB.controller;

import com.example.AtlazDB.dto.ModelRequestDTO;
import com.example.AtlazDB.dto.ModelResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.AtlazDB.model.Model;
import com.example.AtlazDB.service.ModelService;

@RestController
@RequestMapping("/models")
public class ModelController {

    private final ModelService service;

    public ModelController(ModelService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ModelResponseDTO>> list() {
        List<Model> list = service.listAll();
        
        List<ModelResponseDTO> dtoList = list.stream()
                .map(ModelResponseDTO::new)
                .toList();
                
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelResponseDTO> searchById(@PathVariable Long id) {
        return service.searchById(id)
                .map(ModelResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ModelResponseDTO> create(@RequestBody ModelRequestDTO dto) {
        Model saved = service.save(dto);
        return ResponseEntity.ok(new ModelResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelResponseDTO> update(@PathVariable Long id, @RequestBody ModelRequestDTO dto) {
        Model updated = service.update(id, dto);
        return ResponseEntity.ok(new ModelResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
