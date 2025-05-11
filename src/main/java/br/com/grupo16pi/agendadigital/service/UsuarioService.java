package br.com.grupo16pi.agendadigital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupo16pi.agendadigital.DTOs.UsuarioUpdateDTO;
import br.com.grupo16pi.agendadigital.model.Usuario;
import br.com.grupo16pi.agendadigital.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service // Indica que esta classe é um serviço do Spring.
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Lida com o acesso aos dados de Usuario.

    public Optional<Usuario> findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public Optional<Usuario> findByNumeroSus(String sus) {
        return usuarioRepository.findByNumeroSus(sus);
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
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

    if (dto.getNome() != null) usuario.setNome(dto.getNome());
    if (dto.getDataNascimento() != null) usuario.setDataNascimento(dto.getDataNascimento());
    if (dto.getCpf() != null) usuario.setCpf(dto.getCpf());
    if (dto.getNumeroSus() != null) usuario.setNumeroSus(dto.getNumeroSus());
    if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());
    if (dto.getLogradouro() != null) usuario.setLogradouro(dto.getLogradouro());
    if (dto.getNumero() != null) usuario.setNumero(dto.getNumero());
    if (dto.getCep() != null) usuario.setCep(dto.getCep());
    if (dto.getBairro() != null) usuario.setBairro(dto.getBairro());
    if (dto.getCidade() != null) usuario.setCidade(dto.getCidade());
    if (dto.getUf() != null) usuario.setUf(dto.getUf());

    return usuarioRepository.save(usuario);
    } 
}