package br.com.grupo16pi.agendadigital.repository;

import br.com.grupo16pi.agendadigital.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository; // Ensure this import is correct and JpaRepository is available
import org.springframework.stereotype.Repository;

@Repository // Indica que essa interface é um componente de acesso a dados (DAO)
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // Extende JpaRepository, herdando métodos prontos para CRUD com a entidade Agendamento
}