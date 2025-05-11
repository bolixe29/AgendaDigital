package br.com.grupo16pi.agendadigital.repository;

import br.com.grupo16pi.agendadigital.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByNumeroSus(String numeroSus);
}