package com.example.AtlazDB.service;

import com.example.AtlazDB.dto.OrderServiceRequestDTO;
import com.example.AtlazDB.model.User;
import com.example.AtlazDB.model.Vehicle;
import com.example.AtlazDB.repository.UsuarioRepository;
import com.example.AtlazDB.repository.ViaturaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.AtlazDB.model.OrderService;
import com.example.AtlazDB.repository.OrdemServicoRepository;

@Service
public class OrderServiceService {

    private final OrdemServicoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ViaturaRepository viaturaRepository;

    public OrderServiceService(OrdemServicoRepository repository, UsuarioRepository usuarioRepository, ViaturaRepository viaturaRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.viaturaRepository = viaturaRepository;
    }

    public List<OrderService> listAll() {
        return repository.findAll();
    }

    public Optional<OrderService> searchById(Long id) {
        return repository.findById(id);
    }

    public List<OrderService> searchByMonthAndYear(int mes, int ano) {
        LocalDateTime inicio = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fim = inicio.plusMonths(1);
        return repository.buscarPorPeriodo(inicio, fim);
    }

    public OrderService save(OrderServiceRequestDTO dto) {
        User user = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(()->new RuntimeException("User não encontrado!"));
        Vehicle vehicle = viaturaRepository.findById(dto.getIdViatura()).orElseThrow(()->new RuntimeException("Vehicle não encontrada!"));

        OrderService os = new OrderService();
        os.setTipoServico(dto.getTipoServico());
        os.setLocalDestino(dto.getLocalDestino());
        os.setJustificativa(dto.getJustificativa());
        os.setRequisitante(dto.getRequisitante());
        os.setKmSaida(dto.getKmSaida());
        os.setKmChegada(dto.getKmChegada());
        os.setDataSaida(dto.getDataSaida());
        os.setDataRetorno(dto.getDataRetorno());
        os.setUser(user);
        os.setVehicle(vehicle);
        return repository.save(os);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public OrderService update(Long id, OrderServiceRequestDTO dto) {
        OrderService orderService = repository.findById(id).orElseThrow(()-> new RuntimeException("Ordem de Serviço não encontrada!"));
        User user = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(()->new RuntimeException("User não encontrado!"));
        Vehicle vehicle = viaturaRepository.findById(dto.getIdViatura()).orElseThrow(()->new RuntimeException("Vehicle não encontrada!"));

        orderService.setTipoServico(dto.getTipoServico());
        orderService.setLocalDestino(dto.getLocalDestino());
        orderService.setJustificativa(dto.getJustificativa());
        orderService.setRequisitante(dto.getRequisitante());
        orderService.setKmSaida(dto.getKmSaida());
        orderService.setKmChegada(dto.getKmChegada());
        orderService.setDataSaida(dto.getDataSaida());
        orderService.setDataRetorno(dto.getDataRetorno());
        orderService.setUser(user);
        orderService.setVehicle(vehicle);
        return repository.save(orderService);
    }
}
