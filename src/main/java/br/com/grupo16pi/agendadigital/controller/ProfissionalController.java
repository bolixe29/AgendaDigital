package br.com.grupo16pi.agendadigital.controller;

import br.com.grupo16pi.agendadigital.model.Profissional;
import br.com.grupo16pi.agendadigital.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // API REST para Profissionais
@RequestMapping("/profissionais") // Caminho: /profissionais
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService; // Lida com a lógica de profissionais

    @GetMapping // GET em /profissionais: Lista todos
    public List<Profissional> findAll() {
        return profissionalService.findAll();
    }

    @GetMapping("/{id}") // GET em /profissionais/{id}: Busca profissional por ID
    public Optional<Profissional> findById(@PathVariable Long id) {
        return profissionalService.findById(id);
    }

    @PostMapping // POST em /profissionais: Salva novo profissional
    public Profissional save(@RequestBody Profissional profissional) {
        return profissionalService.save(profissional);
    }

    @PutMapping("/{id}") // PUT em /profissionais/{id}: Atualiza profissional por ID
    public Profissional update(@PathVariable Long id, @RequestBody Profissional profissional) {
        profissional.setId(id);
        return profissionalService.save(profissional);
    }

    @DeleteMapping("/{id}") // DELETE em /profissionais/{id}: Deleta profissional por ID
    public void deleteById(@PathVariable Long id) {
        profissionalService.deleteById(id);
    }
}