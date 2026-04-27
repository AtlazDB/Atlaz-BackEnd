package com.example.AtlazDB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AtlazDB.dto.OrderServiceRequestDTO;
import com.example.AtlazDB.dto.OrderServiceResponseDTO;
import com.example.AtlazDB.enums.TypeOccurrence;
import com.example.AtlazDB.model.Fuel;
import com.example.AtlazDB.model.OrderService;
import com.example.AtlazDB.repository.FuelRepository;
import com.example.AtlazDB.service.FuelService;
import com.example.AtlazDB.service.CreateCsv;
import com.example.AtlazDB.service.OrderServiceService;

@RestController
@RequestMapping("/service-orders")
public class OrderServiceController {

    private final OrderServiceService service;
    private final FuelService fuelService;
    private final CreateCsv createCsv;

    public OrderServiceController(OrderServiceService service,
                                  FuelService fuelService,
                                  CreateCsv createCsv) {
        this.service = service;
        this.fuelService = fuelService;
        this.createCsv = createCsv;
    }

    @GetMapping
    public ResponseEntity<List<OrderServiceResponseDTO>> list() {
        List<OrderService> list = service.listAll();
        
        List<OrderServiceResponseDTO> dtoList = list.stream()
                .map(OrderServiceResponseDTO::new)
                .toList();
                
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/tipos-ocorrencia")
    public ResponseEntity<TypeOccurrence[]> listTypes() {
        return ResponseEntity.ok(TypeOccurrence.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderServiceResponseDTO> searchById(@PathVariable Long id) {
        return service.searchById(id)
                .map(OrderServiceResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderServiceResponseDTO> create(@RequestBody OrderServiceRequestDTO dto) {
        OrderService saved = service.save(dto);
        return ResponseEntity.ok(new OrderServiceResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderServiceResponseDTO> update(@PathVariable Long id, @RequestBody OrderServiceRequestDTO dto) {
        OrderService updated = service.update(id, dto);
        return ResponseEntity.ok(new OrderServiceResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private FuelRepository fuelRepository;

    @GetMapping("/csv")
    public ResponseEntity<byte[]> downloadCSV(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        List<OrderService> orders = (month != null && year != null)
                ? service.searchByMonthAndYear(month, year)
                : service.listAll();

        List<Fuel> fuels = (month != null && year != null)
                ? fuelService.searchByMonthAndYear(month, year)
                : fuelService.listAll();

        byte[] csvBytes = createCsv.createCSV(orders, fuels);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=registros.csv")
                .header("Content-Type", "text/csv; charset=UTF-8")
                .body(csvBytes);
    }

    @GetMapping("/por-month")
    public ResponseEntity<List<OrderServiceResponseDTO>> searchByMonth(
            @RequestParam int month,
            @RequestParam int year) {
            
        List<OrderService> list = service.searchByMonthAndYear(month, year);
        
        List<OrderServiceResponseDTO> dtoList = list.stream()
                .map(OrderServiceResponseDTO::new)
                .toList();
                
        return ResponseEntity.ok(dtoList);
    }
}
