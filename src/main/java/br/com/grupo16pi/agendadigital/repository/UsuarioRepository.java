package br.com.grupo16pi.agendadigital.repository;

import br.com.grupo16pi.agendadigital.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}

