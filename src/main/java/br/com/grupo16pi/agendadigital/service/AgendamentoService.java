package br.com.grupo16pi.agendadigital.service;

import br.com.grupo16pi.agendadigital.model.Agendamento;
import br.com.grupo16pi.agendadigital.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<Agendamento> findAll() {
        return agendamentoRepository.findAll();
    }

    public Optional<Agendamento> findById(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento save(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public void deleteById(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public List<Agendamento> findByData(LocalDate data) {
        return agendamentoRepository.findByData(data);
    }

    public List<Agendamento> findByUsuarioNome(String nome) {
        return agendamentoRepository.findByUsuarioNome(nome);
    }

    public Optional<Agendamento> findByIdWithRelations(Long id) {
        return agendamentoRepository.findByIdWithRelations(id);
    }
}