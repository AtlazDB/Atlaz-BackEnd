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
import com.example.AtlazDB.repository.CidadeRepository;
import com.example.AtlazDB.repository.OrdemServicoRepository;
import com.example.AtlazDB.repository.UsuarioRepository;
import com.example.AtlazDB.repository.ViaturaRepository;

@Service
public class FuelService {

    private final FuelRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ViaturaRepository viaturaRepository;
    private final CidadeRepository cidadeRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public FuelService(FuelRepository repository, UsuarioRepository usuarioRepository, ViaturaRepository viaturaRepository, CidadeRepository cidadeRepository, OrdemServicoRepository ordemServicoRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.viaturaRepository = viaturaRepository;
        this.cidadeRepository = cidadeRepository;
        this.ordemServicoRepository = ordemServicoRepository;
    }

    public List<Fuel> listAll() {
        return repository.findAll();
    }

    public Optional<Fuel> searchById(Long id) {
        return repository.findById(id);
    }

    public Fuel save(FuelRequestDTO dto) {
        User user = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(()-> new RuntimeException("User não encontrado."));
        Vehicle vehicle = viaturaRepository.findById(dto.getIdViatura()).orElseThrow(()-> new RuntimeException("Vehicle não encontrada."));
        City city = cidadeRepository.findById(dto.getIdCidade()).orElseThrow(()-> new RuntimeException("City não encontrada."));
        OrderService os = ordemServicoRepository.findById(dto.getIdOs()).orElseThrow(()-> new RuntimeException("Ordem de serviço não encontrada."));

        Fuel fuel = new Fuel();
        fuel.setValorTotal(dto.getValorTotal());
        fuel.setCurrentKm(os.getKmChegada());
        fuel.setLitros(dto.getLitros());
        fuel.setDateHour(dto.getDataHora());
        fuel.setNumeroNotaFiscal(dto.getNumeroNotaFiscal());
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
            Double valorTotal,
            Double kmAtual
    ) {

        if (typeFuel != null) {
            return repository.findByTipoCombustivel(typeFuel);
        }

        if (valorTotal != null) {
            return repository.findByValorTotalGreaterThan(valorTotal);
        }

        if (kmAtual != null) {
            return repository.findByKmAtualGreaterThan(kmAtual);
        }

        return repository.findAll();
    }

    public Fuel update(Long id, FuelRequestDTO dto) {
        User user = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(()-> new RuntimeException("User não encontrado."));
        Vehicle vehicle = viaturaRepository.findById(dto.getIdViatura()).orElseThrow(()-> new RuntimeException("Vehicle não encontrada."));
        City city = cidadeRepository.findById(dto.getIdCidade()).orElseThrow(()-> new RuntimeException("City não encontrada."));
        Fuel fuel = repository.findById(id).orElseThrow(()-> new RuntimeException("Fuel não encontrado."));
        OrderService os = ordemServicoRepository.findById(dto.getIdOs()).orElseThrow(()-> new RuntimeException("Ordem de serviço não encontrada."));


        fuel.setValorTotal(dto.getValorTotal());
        fuel.setCurrentKm(os.getKmChegada());
        fuel.setLitros(dto.getLitros());
        fuel.setDateHour(dto.getDataHora());
        fuel.setNumeroNotaFiscal(dto.getNumeroNotaFiscal());
        fuel.setUser(user);
        fuel.setVehicle(vehicle);
        fuel.setCity(city);
        fuel.setOrderService(os);

        return repository.save(fuel);

    }
    public List<Fuel> searchByMonthAndYear(int mes, int ano) {

        LocalDateTime inicio = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fim = inicio.plusMonths(1); // início do mês seguinte, exclusive

        return repository.buscarPorPeriodo(inicio, fim);
    }

    public List<FuelResponseDTO> list() {
        List<Fuel> lista = repository.findAll();

        return lista.stream()
                .map(FuelResponseDTO::fromEntity)
                .toList();
    }
}