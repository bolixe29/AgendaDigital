package br.com.grupo16pi.agendadigital.repository;

import br.com.grupo16pi.agendadigital.model.Agendamento;

import java.time.LocalDate;
import java.util.List; // Corrigida a importação para java.util.List

import org.springframework.data.jpa.repository.JpaRepository; // Ensure this import is correct and JpaRepository is available
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // Indica que essa interface é um componente de acesso a dados (DAO)
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // Extende JpaRepository, herdando métodos prontos para CRUD com a entidade Agendamento

    @Query("SELECT a FROM Agendamento a WHERE a.usuario.nome ILIKE %:nome%")
    List<Agendamento> findByUsuarioNome(@Param("nome") String nome);

    List<Agendamento> findByData(LocalDate data);

    @Query("SELECT a FROM Agendamento a WHERE a.especialidade.id = :especialidadeId AND a.data = :data")
    List<Agendamento> findByEspecialidadeIdAndData(@Param("especialidadeId") Long especialidadeId, @Param("data") LocalDate data);
    // Método para buscar agendamentos por ID de especialidade e data
}