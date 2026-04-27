package com.example.AtlazDB.service;

import com.example.AtlazDB.dto.OrderServiceRequestDTO;
import com.example.AtlazDB.model.User;
import com.example.AtlazDB.model.Vehicle;
import com.example.AtlazDB.repository.UserRepository;
import com.example.AtlazDB.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.AtlazDB.model.OrderService;
import com.example.AtlazDB.repository.OrderServiceRepository;

@Service
public class OrderServiceService {

    private final OrderServiceRepository repository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public OrderServiceService(OrderServiceRepository repository, UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<OrderService> listAll() {
        return repository.findAll();
    }

    public Optional<OrderService> searchById(Long id) {
        return repository.findById(id);
    }

    public List<OrderService> searchByMonthAndYear(int month, int year) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);
        return repository.searchByPeriod(start, end);
    }

    public OrderService save(OrderServiceRequestDTO dto) {
        User user = userRepository.findById(dto.getIdUser()).orElseThrow(()->new RuntimeException("User não not found"));
        Vehicle vehicle = vehicleRepository.findById(dto.getIdVehicle()).orElseThrow(()->new RuntimeException("Vehicle não not found"));

        OrderService os = new OrderService();
        os.setTypeService(dto.getTypeService());
        os.setLocalDestiny(dto.getLocalDestiny());
        os.setJustification(dto.getJustification());
        os.setRequisition(dto.getRequisition());
        os.setLeaveKm(dto.getLeaveKm());
        os.setArriveKm(dto.getArriveKm());
        os.setLeaveDate(dto.getLeaveDate());
        os.setReturnDate(dto.getReturnDate());
        os.setUser(user);
        os.setVehicle(vehicle);
        return repository.save(os);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public OrderService update(Long id, OrderServiceRequestDTO dto) {
        OrderService orderService = repository.findById(id).orElseThrow(()-> new RuntimeException("Service Order not found"));
        User user = userRepository.findById(dto.getIdUser()).orElseThrow(()->new RuntimeException("User not found"));
        Vehicle vehicle = vehicleRepository.findById(dto.getIdVehicle()).orElseThrow(()->new RuntimeException("Vehicle not found"));

        orderService.setTypeService(dto.getTypeService());
        orderService.setLocalDestiny(dto.getLocalDestiny());
        orderService.setJustification(dto.getJustification());
        orderService.setRequisition(dto.getRequisition());
        orderService.setLeaveKm(dto.getLeaveKm());
        orderService.setArriveKm(dto.getArriveKm());
        orderService.setLeaveDate(dto.getLeaveDate());
        orderService.setReturnDate(dto.getReturnDate());
        orderService.setUser(user);
        orderService.setVehicle(vehicle);
        return repository.save(orderService);
    }
}
