package br.com.grupo16pi.agendadigital.repository;

import br.com.grupo16pi.agendadigital.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    
}