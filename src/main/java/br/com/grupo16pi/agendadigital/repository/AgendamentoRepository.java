package br.com.grupo16pi.agendadigital.repository;

import br.com.grupo16pi.agendadigital.model.Agendamento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a JOIN FETCH a.usuario JOIN FETCH a.especialidade LEFT JOIN FETCH a.profissional WHERE a.id = :id")
    Optional<Agendamento> findByIdWithRelations(@Param("id") Long id);

    @Query("SELECT a FROM Agendamento a WHERE a.usuario.nome ILIKE %:nome%")
    List<Agendamento> findByUsuarioNome(@Param("nome") String nome);

    List<Agendamento> findByData(LocalDate data);

    @Query("SELECT a FROM Agendamento a WHERE a.especialidade.id = :especialidadeId AND a.data = :data")
    List<Agendamento> findByEspecialidadeIdAndData(@Param("especialidadeId") Long especialidadeId, @Param("data") LocalDate data);
}