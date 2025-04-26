package br.com.grupo16pi.agendadigital.repository;

import br.com.grupo16pi.agendadigital.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca a interface como um repositório do Spring (componente para acesso a dados)
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    // Herda métodos de CRUD prontos para a entidade Especialidade
}