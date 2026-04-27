package com.example.AtlazDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.AtlazDB.dto.ModelRequestDTO;
import com.example.AtlazDB.model.Model;
import com.example.AtlazDB.repository.ModelRepository;

@Service
public class ModelService {

    private final ModelRepository repository;

    public ModelService(ModelRepository repository) {
        this.repository = repository;
    }

    public List<Model> listAll() {
        return repository.findAll();
    }

    public Optional<Model> searchById(Long id) {
        return repository.findById(id);
    }

    public Model save(ModelRequestDTO dto) {
        Model model = new Model();
        model.setNameModel(dto.getNameModel());
        model.setNameBrand(dto.getNameBrand());
        return repository.save(model);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Model update(Long id, ModelRequestDTO dto) {
        Model model = repository.findById(id).orElseThrow(()-> new RuntimeException("Model not found"));
        model.setNameModel(dto.getNameModel());
        model.setNameBrand(dto.getNameBrand());
        return repository.save(model);
    }
}
