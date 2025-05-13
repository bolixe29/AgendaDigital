package br.com.grupo16pi.agendadigital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupo16pi.agendadigital.DTOs.UsuarioUpdateDTO;
import br.com.grupo16pi.agendadigital.model.Usuario;
import br.com.grupo16pi.agendadigital.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service // Indica que esta classe é um serviço do Spring.
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Lida com o acesso aos dados de Usuario.

    public Optional<Usuario> findByCpf(String cpf) { // Busca um usuário pelo CPF.
        return usuarioRepository.findByCpf(cpf);
    }

    public Optional<Usuario> findByNumeroSus(String sus) {  // Busca um usuário pelo número do SUS.
        return usuarioRepository.findByNumeroSus(sus);
    }

    public List<Usuario> findByNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome); // Busca usuários pelo nome, ignorando maiúsculas e minúsculas.
    }    

    public List<Usuario> findAll() { // Busca todos os usuários.
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) { // Busca um usuário pelo ID.
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) { // Salva ou atualiza um usuário.
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) { // Deleta um usuário pelo ID.
        usuarioRepository.deleteById(id);
    }
    @Transactional
    public Usuario updatePartial(Long id, UsuarioUpdateDTO dto) {
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

    if (dto.getNome() != null) usuario.setNome(dto.getNome());
    if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());
    if (dto.getLogradouro() != null) usuario.setLogradouro(dto.getLogradouro());
    if (dto.getNumero() != null) usuario.setNumero(dto.getNumero());
    if (dto.getComplemento() != null) usuario.setComplemento(dto.getComplemento());
    if (dto.getCep() != null) usuario.setCep(dto.getCep());
    if (dto.getBairro() != null) usuario.setBairro(dto.getBairro());
    if (dto.getCidade() != null) usuario.setCidade(dto.getCidade());
    if (dto.getTelCelular() != null) usuario.setTelCelular(dto.getTelCelular());
    if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());

    return usuarioRepository.save(usuario);
    }
}