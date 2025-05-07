package br.com.grupo16pi.agendadigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupo16pi.agendadigital.model.MatrizAgenda;

@Repository
public interface MatrizAgendaRepository extends JpaRepository<MatrizAgenda, Long> {
    
    
}
