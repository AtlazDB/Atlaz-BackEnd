package com.example.AtlazDB.controller;

import java.util.List;

import com.example.AtlazDB.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AtlazDB.dto.UserRequestDTO;
import com.example.AtlazDB.dto.UserResponseDTO;
import com.example.AtlazDB.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() {
        List<User> list = service.listAll();
        
        List<UserResponseDTO> dtoList = list.stream()
                .map(UserResponseDTO::new)
                .toList();
                
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> searchById(@PathVariable Long id) {
        return service.searchById(id)
                .map(UserResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto) {
        User saved = service.save(dto);
        return ResponseEntity.ok(new UserResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        User updated = service.update(id, dto);
        return ResponseEntity.ok(new UserResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
