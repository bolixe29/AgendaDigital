package br.com.grupo16pi.agendadigital.service;

import br.com.grupo16pi.agendadigital.model.Especialidade;
import br.com.grupo16pi.agendadigital.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um serviço do Spring.
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository; // Lida com o acesso aos dados de Especialidade.

    public List<Especialidade> findAll() { // Busca todas as especialidades.
        return especialidadeRepository.findAll();
    }

    public Optional<Especialidade> findById(Long id) { // Busca uma especialidade pelo ID.
        return especialidadeRepository.findById(id);
    }

    public Especialidade save(Especialidade especialidade) { // Salva ou atualiza uma especialidade.
        return especialidadeRepository.save(especialidade);
    }

    public void deleteById(Long id) { // Deleta uma especialidade pelo ID.
        especialidadeRepository.deleteById(id);
    }
}