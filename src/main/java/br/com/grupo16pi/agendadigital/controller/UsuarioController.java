package br.com.grupo16pi.agendadigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupo16pi.agendadigital.DTOs.UsuarioRequestDTO;
import br.com.grupo16pi.agendadigital.DTOs.UsuarioResponseDTO;
import br.com.grupo16pi.agendadigital.model.Usuario;
import br.com.grupo16pi.agendadigital.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioResponseDTO> findAll() {
        return usuarioService.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return toResponseDTO(usuario);
    }

    @PostMapping
    public UsuarioResponseDTO save(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = toEntity(usuarioRequestDTO);
        Usuario saved = usuarioService.save(usuario);
        return toResponseDTO(saved);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO update(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = toEntity(usuarioRequestDTO);
        usuario.setId(id);
        Usuario updated = usuarioService.save(usuario);
        return toResponseDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }

    // Conversores

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setNumeroSus(usuario.getNumeroSus());
        return dto;
    }

    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setCpf(dto.getCpf());
        usuario.setNumeroSus(dto.getNumeroSus());
        usuario.setTelefone(dto.getTelefone());
        usuario.setLogradouro(dto.getLogradouro());
        usuario.setNumero(dto.getNumero());
        usuario.setCep(dto.getCep());
        usuario.setBairro(dto.getBairro());
        usuario.setCidade(dto.getCidade());
        usuario.setUf(br.com.grupo16pi.agendadigital.enums.UfEnum.valueOf(dto.getUf()));
        return usuario;
    }
}
