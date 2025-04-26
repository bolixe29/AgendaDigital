package br.com.grupo16pi.agendadigital.controller;

import br.com.grupo16pi.agendadigital.model.MatrizAgenda;
import br.com.grupo16pi.agendadigital.service.MatrizAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // API REST para Matriz de Agendas
@RequestMapping("/matrizes-agenda") // Caminho: /matrizes-agenda
public class MatrizAgendaController {

    @Autowired
    private MatrizAgendaService matrizAgendaService; // Lida com a lógica da matriz de agendas

    @GetMapping // GET em /matrizes-agenda: Lista todas
    public List<MatrizAgenda> findAll() {
        return matrizAgendaService.findAll();
    }

    @GetMapping("/{id}") // GET em /matrizes-agenda/{id}: Busca matriz por ID
    public Optional<MatrizAgenda> findById(@PathVariable Long id) {
        return matrizAgendaService.findById(id);
    }

    @PostMapping // POST em /matrizes-agenda: Salva nova matriz
    public MatrizAgenda save(@RequestBody MatrizAgenda matrizAgenda) {
        return matrizAgendaService.save(matrizAgenda);
    }

    @PutMapping("/{id}") // PUT em /matrizes-agenda/{id}: Atualiza matriz por ID
    public MatrizAgenda update(@PathVariable Long id, @RequestBody MatrizAgenda matrizAgenda) {
        matrizAgenda.setId(id);
        return matrizAgendaService.save(matrizAgenda);
    }

    @DeleteMapping("/{id}") // DELETE em /matrizes-agenda/{id}: Deleta matriz por ID
    public void deleteById(@PathVariable Long id) {
        matrizAgendaService.deleteById(id);
    }
}