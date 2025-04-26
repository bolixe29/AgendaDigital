package br.com.grupo16pi.agendadigital.controller;

import br.com.grupo16pi.agendadigital.model.Agendamento;
import br.com.grupo16pi.agendadigital.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // API REST para Agendamentos
@RequestMapping("/agendamentos") // Caminho: /agendamentos
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService; // Lida com a lógica de agendamentos

    @GetMapping // GET em /agendamentos: Lista todos
    public List<Agendamento> findAll() {
        return agendamentoService.findAll();
    }

    @GetMapping("/{id}") // GET em /agendamentos/{id}: Busca agendamento por ID
    public Optional<Agendamento> findById(@PathVariable Long id) {
        return agendamentoService.findById(id);
    }

    @PostMapping // POST em /agendamentos: Salva novo agendamento
    public Agendamento save(@RequestBody Agendamento agendamento) {
        return agendamentoService.save(agendamento);
    }

    @PutMapping("/{id}") // PUT em /agendamentos/{id}: Atualiza agendamento por ID
    public Agendamento update(@PathVariable Long id, @RequestBody Agendamento agendamento) {
        agendamento.setId(id);
        return agendamentoService.save(agendamento);
    }

    @DeleteMapping("/{id}") // DELETE em /agendamentos/{id}: Deleta agendamento por ID
    public void deleteById(@PathVariable Long id) {
        agendamentoService.deleteById(id);
    }
}