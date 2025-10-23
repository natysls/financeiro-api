package com.sop.naty.financeiro.service;

import com.sop.naty.financeiro.dto.UsuarioDTO;
import com.sop.naty.financeiro.entity.Usuario;
import com.sop.naty.financeiro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioDTO criar(UsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já existe");
        }
        Usuario user = fromDTO(dto);
        return toDTO(usuarioRepository.save(user));
    }

    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        existente.setEmail(dto.getEmail());
        existente.setSenha(dto.getSenha());
        existente.setRole(dto.getRole());

        return toDTO(usuarioRepository.save(existente));
    }

    public void deletar(Long id) {
        Usuario despesa = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        usuarioRepository.delete(despesa);
    }

    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrada"));
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Conversão manual DTO ↔ Entidade
    private UsuarioDTO toDTO(Usuario user) {
        return UsuarioDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .senha(user.getSenha())
                .build();
    }

    private Usuario fromDTO(UsuarioDTO dto) {
        return Usuario.builder()
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .role(dto.getRole())
                .build();
    }
}
