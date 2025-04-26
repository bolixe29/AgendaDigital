package br.com.grupo16pi.agendadigital.controller;

import br.com.grupo16pi.agendadigital.model.Especialidade;
import br.com.grupo16pi.agendadigital.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // API REST para Especialidades
@RequestMapping("/especialidades") // Caminho: /especialidades
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService; // Lida com a lógica de especialidades

    @GetMapping // GET em /especialidades: Lista todas
    public List<Especialidade> findAll() {
        return especialidadeService.findAll();
    }

    @GetMapping("/{id}") // GET em /especialidades/{id}: Busca especialidade por ID
    public Optional<Especialidade> findById(@PathVariable Long id) {
        return especialidadeService.findById(id);
    }

    @PostMapping // POST em /especialidades: Salva nova especialidade
    public Especialidade save(@RequestBody Especialidade especialidade) {
        return especialidadeService.save(especialidade);
    }

    @PutMapping("/{id}") // PUT em /especialidades/{id}: Atualiza especialidade por ID
    public Especialidade update(@PathVariable Long id, @RequestBody Especialidade especialidade) {
        especialidade.setId(id);
        return especialidadeService.save(especialidade);
    }

    @DeleteMapping("/{id}") // DELETE em /especialidades/{id}: Deleta especialidade por ID
    public void deleteById(@PathVariable Long id) {
        especialidadeService.deleteById(id);
    }
}