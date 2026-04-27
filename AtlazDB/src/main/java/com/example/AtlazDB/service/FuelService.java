package com.example.AtlazDB.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.AtlazDB.model.*;
import org.springframework.stereotype.Service;

import com.example.AtlazDB.dto.FuelRequestDTO;
import com.example.AtlazDB.dto.FuelResponseDTO;
import com.example.AtlazDB.enums.TypeFuel;
import com.example.AtlazDB.model.Fuel;
import com.example.AtlazDB.repository.FuelRepository;
import com.example.AtlazDB.repository.CityRepository;
import com.example.AtlazDB.repository.OrderServiceRepository;
import com.example.AtlazDB.repository.UserRepository;
import com.example.AtlazDB.repository.VehicleRepository;

@Service
public class FuelService {

    private final FuelRepository repository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final CityRepository cityRepository;
    private final OrderServiceRepository orderServiceRepository;

    public FuelService(FuelRepository repository, UserRepository userRepository, VehicleRepository vehicleRepository, CityRepository cityRepository, OrderServiceRepository orderServiceRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.cityRepository = cityRepository;
        this.orderServiceRepository = orderServiceRepository;
    }

    public List<Fuel> listAll() {
        return repository.findAll();
    }

    public Optional<Fuel> searchById(Long id) {
        return repository.findById(id);
    }

    public Fuel save(FuelRequestDTO dto) {
        User user = userRepository.findById(dto.getIdUser()).orElseThrow(()-> new RuntimeException("User não encontrado."));
        Vehicle vehicle = vehicleRepository.findById(dto.getIdVehicle()).orElseThrow(()-> new RuntimeException("Vehicle não encontrada."));
        City city = cityRepository.findById(dto.getIdCity()).orElseThrow(()-> new RuntimeException("City não encontrada."));
        OrderService os = orderServiceRepository.findById(dto.getIdOs()).orElseThrow(()-> new RuntimeException("Ordem de serviço não encontrada."));

        Fuel fuel = new Fuel();
        fuel.setTotalValue(dto.getTotalValue());
        fuel.setCurrentKm(os.getArriveKm());
        fuel.setLiters(dto.getLiters());
        fuel.setDateHour(dto.getDateHour());
        fuel.setNumberReceipt(dto.getNumberReceipt());
        fuel.setUser(user);
        fuel.setVehicle(vehicle);
        fuel.setCity(city);
        fuel.setOrderService(os);

        return repository.save(fuel);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Fuel> searchWithFilters(
            TypeFuel typeFuel,
            Double totalValue,
            Double currentKm
    ) {

        if (typeFuel != null) {
            return repository.findByTypeFuel(typeFuel);
        }

        if (totalValue != null) {
            return repository.findByTotalValueGreaterThan(totalValue);
        }

        if (currentKm != null) {
            return repository.findByCurrentKmGreaterThan(currentKm);
        }

        return repository.findAll();
    }

    public Fuel update(Long id, FuelRequestDTO dto) {
        User user = userRepository.findById(dto.getIdUser()).orElseThrow(()-> new RuntimeException("User not found."));
        Vehicle vehicle = vehicleRepository.findById(dto.getIdVehicle()).orElseThrow(()-> new RuntimeException("Vehicle not found."));
        City city = cityRepository.findById(dto.getIdCity()).orElseThrow(()-> new RuntimeException("City not found."));
        Fuel fuel = repository.findById(id).orElseThrow(()-> new RuntimeException("Fuel not found."));
        OrderService os = orderServiceRepository.findById(dto.getIdOs()).orElseThrow(()-> new RuntimeException("Service Order not found."));


        fuel.setTotalValue(dto.getTotalValue());
        fuel.setCurrentKm(os.getArriveKm());
        fuel.setLiters(dto.getLiters());
        fuel.setDateHour(dto.getDateHour());
        fuel.setNumberReceipt(dto.getNumberReceipt());
        fuel.setUser(user);
        fuel.setVehicle(vehicle);
        fuel.setCity(city);
        fuel.setOrderService(os);

        return repository.save(fuel);

    }
    public List<Fuel> searchByMonthAndYear(int month, int year) {

        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1); // start of the next month, exclusive

        return repository.searchByPeriod(start, end);
    }

    public List<FuelResponseDTO> list() {
        List<Fuel> list = repository.findAll();

        return list.stream()
                .map(FuelResponseDTO::fromEntity)
                .toList();
    }
}