package br.com.grupo16pi.agendadigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupo16pi.agendadigital.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByNumeroSus(String numeroSus);
}

