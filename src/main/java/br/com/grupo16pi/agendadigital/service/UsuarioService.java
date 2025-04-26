package br.com.grupo16pi.agendadigital.service;

import br.com.grupo16pi.agendadigital.model.Usuario;
import br.com.grupo16pi.agendadigital.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um serviço do Spring.
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Lida com o acesso aos dados de Usuario.

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
}