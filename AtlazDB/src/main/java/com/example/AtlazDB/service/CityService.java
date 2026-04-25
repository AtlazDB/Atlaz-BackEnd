package com.example.AtlazDB.service;

import java.util.List;
import java.util.Optional;

import com.example.AtlazDB.model.City;
import org.springframework.stereotype.Service;

import com.example.AtlazDB.dto.CityRequestDTO;
import com.example.AtlazDB.repository.CidadeRepository;

@Service
public class CityService {

    private final CidadeRepository repository;

    public CityService(CidadeRepository repository) {
        this.repository = repository;
    }

    public List<City> ListAll() {
        return repository.findAll();
    }

    public Optional<City> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public City save(CityRequestDTO dto) {
        City city = new City();
        city.setNome(dto.getNome());
        city.setUf(dto.getUf());
        return repository.save(city);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public City update(Long id, CityRequestDTO dto) {
        City city = repository.findById(id).orElseThrow(()-> new RuntimeException("City não encontrada"));
        city.setNome(dto.getNome());
        city.setUf(dto.getUf());
        return repository.save(city);
    }
}
