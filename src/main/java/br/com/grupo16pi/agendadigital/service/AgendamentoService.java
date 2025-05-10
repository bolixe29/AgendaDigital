package br.com.grupo16pi.agendadigital.service;

import br.com.grupo16pi.agendadigital.model.Agendamento;
import br.com.grupo16pi.agendadigital.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um serviço do Spring.
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository; // Lida com o acesso aos dados de Agendamento.

    public List<Agendamento> findAll() { // Busca todos os agendamentos.
        return agendamentoRepository.findAll();
    }

    public Optional<Agendamento> findById(Long id) { // Busca um agendamento pelo ID.
        return agendamentoRepository.findById(id);
    }

    public Agendamento save(Agendamento agendamento) { // Salva ou atualiza um agendamento.
        return agendamentoRepository.save(agendamento);
    }

    public void deleteById(Long id) { // Deleta um agendamento pelo ID.
        agendamentoRepository.deleteById(id);
    }
    public List<Agendamento> findByData(LocalDate data) {
    return agendamentoRepository.findByData(data);
    }   
    public List<Agendamento> findByUsuarioNome(String nome) {
    return agendamentoRepository.findByUsuarioNome(nome);
}

}