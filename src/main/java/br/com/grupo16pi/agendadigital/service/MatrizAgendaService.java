package br.com.grupo16pi.agendadigital.service;

import br.com.grupo16pi.agendadigital.model.MatrizAgenda;
import br.com.grupo16pi.agendadigital.repository.MatrizAgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um serviço do Spring.
public class MatrizAgendaService {

    @Autowired
    private MatrizAgendaRepository matrizAgendaRepository; // Lida com o acesso aos dados de MatrizAgenda.

    public List<MatrizAgenda> findAll() { // Busca todas as matrizes de agenda.
        return matrizAgendaRepository.findAll();
    }

    public Optional<MatrizAgenda> findById(Long id) { // Busca uma matriz de agenda pelo ID.
        return matrizAgendaRepository.findById(id);
    }

    public MatrizAgenda save(MatrizAgenda matrizAgenda) { // Salva ou atualiza uma matriz de agenda.
        return matrizAgendaRepository.save(matrizAgenda);
    }

    public void deleteById(Long id) { // Deleta uma matriz de agenda pelo ID.
        matrizAgendaRepository.deleteById(id);
    }
}