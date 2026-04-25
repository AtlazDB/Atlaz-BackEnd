package com.example.AtlazDB.service;

import com.example.AtlazDB.dto.ViaturaRequestDTO;
import com.example.AtlazDB.enums.ViaturaStatus;
import com.example.AtlazDB.model.Model;
import com.example.AtlazDB.model.Vehicle;
import com.example.AtlazDB.repository.ModeloRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.AtlazDB.repository.ViaturaRepository;

@Service
public class VehicleService {

    private final ViaturaRepository repository;
    private final ModeloRepository modeloRepository;

    public VehicleService(ViaturaRepository repository, ModeloRepository modeloRepository) {
        this.repository = repository;
        this.modeloRepository = modeloRepository;
    }

    public List<Vehicle> listarTodos() {
        return repository.findAll();
    }

    public Optional<Vehicle> searchById(Long id) {
        return repository.findById(id);
    }

    public Vehicle save(ViaturaRequestDTO dto) {
        Model model = modeloRepository.findById(dto.getIdModelo()).orElseThrow(()->new RuntimeException("Model não encontrado!"));
        Vehicle vehicle = new Vehicle();
        vehicle.setTipo(dto.getTipo());
        vehicle.setPrefixo(dto.getPrefixo());
        vehicle.setViaturaStatus(ViaturaStatus.ATIVO);
        vehicle.setModel(model);
        return repository.save(vehicle);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Vehicle update(Long id, ViaturaRequestDTO dto) {
        Vehicle vehicle = repository.findById(id).orElseThrow(()-> new RuntimeException("Vehicle não encontrada."));
        vehicle.setTipo(dto.getTipo());
        vehicle.setPrefixo(dto.getPrefixo());
        vehicle.setViaturaStatus(ViaturaStatus.ATIVO);
        return repository.save(vehicle);
    }
}
