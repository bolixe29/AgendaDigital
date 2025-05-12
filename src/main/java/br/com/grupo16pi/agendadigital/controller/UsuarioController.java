package br.com.grupo16pi.agendadigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupo16pi.agendadigital.DTOs.UsuarioRequestDTO;
import br.com.grupo16pi.agendadigital.DTOs.UsuarioResponseDTO;
import br.com.grupo16pi.agendadigital.DTOs.UsuarioUpdateDTO;
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
    
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> patchUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateDTO dto) {
        Usuario atualizado = usuarioService.updatePartial(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioResponseDTO> findByCpf(@PathVariable String cpf) {
        return usuarioService.findByCpf(cpf)
        .map(this::toResponseDTO)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sus/{sus}")
    public ResponseEntity<UsuarioResponseDTO> findByNumeroSus(@PathVariable String sus) {
        return usuarioService.findByNumeroSus(sus)
        .map(this::toResponseDTO)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome")
    public List<UsuarioResponseDTO> findByNome(@RequestParam String nome) {
        return usuarioService.findByNome(nome).stream().map(this::toResponseDTO).toList();
    }

    // Conversores

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setCpf(usuario.getCpf());
        dto.setNumeroSus(usuario.getNumeroSus());
        dto.setTelefone(usuario.getTelefone());
        dto.setTelCelular(usuario.getTelCelular());
        dto.setEmail(usuario.getEmail());
        dto.setLogradouro(usuario.getLogradouro());
        dto.setNumero(usuario.getNumero());
        dto.setCep(usuario.getCep());
        dto.setBairro(usuario.getBairro());
        dto.setCidade(usuario.getCidade());
        dto.setUf(usuario.getUf());
        return dto;
    }

    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setCpf(dto.getCpf());
        usuario.setNumeroSus(dto.getNumeroSus());
        usuario.setTelefone(dto.getTelefone());
        usuario.setTelCelular(dto.getTelCelular());
        usuario.setEmail(dto.getEmail());
        usuario.setLogradouro(dto.getLogradouro());
        usuario.setNumero(dto.getNumero());
        usuario.setCep(dto.getCep());
        usuario.setBairro(dto.getBairro());
        usuario.setCidade(dto.getCidade());
        usuario.setUf(br.com.grupo16pi.agendadigital.enums.UfEnum.valueOf(dto.getUf()));
        return usuario;
    }
}