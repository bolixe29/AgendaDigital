package br.com.grupo16pi.agendadigital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupo16pi.agendadigital.enums.DiaSemanaEnum;
import br.com.grupo16pi.agendadigital.model.MatrizAgenda;

@Repository
public interface MatrizAgendaRepository extends JpaRepository<MatrizAgenda, Long> {
    
    List<MatrizAgenda> findByEspecialidadeIdAndDisponivelTrue(Long especialidadeId);
    Optional<MatrizAgenda> findByEspecialidadeIdAndDiaSemanaAndDisponivelTrue(Long especialidadeId, DiaSemanaEnum diaSemana);    
}
