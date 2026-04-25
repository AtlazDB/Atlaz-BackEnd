package com.example.AtlazDB.service;

import com.example.AtlazDB.dto.UserRequestDTO;
import com.example.AtlazDB.enums.UsuarioStatus;
import com.example.AtlazDB.model.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.AtlazDB.repository.UsuarioRepository;

@Service
public class UserService {

    private final UsuarioRepository repository;

    public UserService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<User> listAll() {
        return repository.findAll();
    }

    public Optional<User> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public User save(UserRequestDTO dto) {
        User user = new User();

        user.setNome(dto.getNome());
        user.setMatricula(dto.getMatricula());
        user.setSenhaHash(dto.getSenhaHash());
        user.setEmail(dto.getEmail());
        user.setPerfil((dto.getPerfil()));
        user.setUsuarioStatus(UsuarioStatus.ATIVO);
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public User update(Long id, UserRequestDTO dto) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setNome(dto.getNome());
        user.setMatricula(dto.getMatricula());
        user.setSenhaHash(dto.getSenhaHash());
        user.setEmail(dto.getEmail());
        user.setPerfil(dto.getPerfil());
        user.setUsuarioStatus(UsuarioStatus.ATIVO);
        return repository.save(user);
    }
}
