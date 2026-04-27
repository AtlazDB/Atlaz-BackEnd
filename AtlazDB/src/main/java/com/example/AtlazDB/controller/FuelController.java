package com.example.AtlazDB.controller;

import java.util.List;

import com.example.AtlazDB.model.Fuel;
import com.example.AtlazDB.service.CreateCsv;
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

import com.example.AtlazDB.dto.FuelRequestDTO;
import com.example.AtlazDB.dto.FuelResponseDTO;
import com.example.AtlazDB.enums.TypeFuel;
import com.example.AtlazDB.service.FuelService;

@RestController
@RequestMapping("/refueling")
public class FuelController {

    private final FuelService service;
//    private final CreateCsv createCsv;

    public FuelController(FuelService service, CreateCsv createCsv) {
        this.service = service;
//        this.createCsv = createCsv;
    }

    @GetMapping
    public List<Fuel> search(
            @RequestParam(required = false) TypeFuel typeFuel,
            @RequestParam(required = false) Double totalValue,
            @RequestParam(required = false) Double currentKm
    ) {
        return service.searchWithFilters(typeFuel, totalValue, currentKm);
    }


//    @GetMapping("/csv")
//    public ResponseEntity<byte[]> downloadCSV() {
//
//        byte[] csvBytes = gerarCsv.createCSV();
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=abastecimento.csv")
//                .header("Content-Type", "text/csv")
//                .body(csvBytes);
//    }

    @GetMapping("/{id}")
    public Fuel searchById(@PathVariable Long id) {
        return service.searchById(id).orElse(null);
    }

    @PostMapping
    public Fuel create(@RequestBody FuelRequestDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public Fuel update(@PathVariable Long id, @RequestBody FuelRequestDTO dto) {
        return service.update(id,dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/por-month")
    public ResponseEntity<List<Fuel>> searchByMonth(
            @RequestParam int month,
            @RequestParam int year) {

        List<Fuel> list = service.searchByMonthAndYear(month, year);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/dto")
    public List<FuelResponseDTO> listDTO() {
        return service.list();
    }

}