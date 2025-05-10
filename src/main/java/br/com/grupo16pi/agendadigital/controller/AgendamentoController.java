package br.com.grupo16pi.agendadigital.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupo16pi.agendadigital.DTOs.AgendamentoRequestDTO;
import br.com.grupo16pi.agendadigital.model.Agendamento;
import br.com.grupo16pi.agendadigital.repository.EspecialidadeRepository;
import br.com.grupo16pi.agendadigital.repository.ProfissionalRepository;
import br.com.grupo16pi.agendadigital.repository.UsuarioRepository;
import br.com.grupo16pi.agendadigital.service.AgendamentoService;
import jakarta.validation.Valid;

@RestController // API REST para Agendamentos
@RequestMapping("/agendamentos") // Caminho: /agendamentos
public class AgendamentoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

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

    @PostMapping
    public ResponseEntity<Agendamento> save(@RequestBody @Valid AgendamentoRequestDTO dto) {
    Agendamento agendamento = toEntity(dto);
    Agendamento salvo = agendamentoService.save(agendamento);
    return ResponseEntity.ok(salvo);
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
    
    // Método para converter DTO em entidade Agendamento    
    private Agendamento toEntity(AgendamentoRequestDTO dto) {
        Agendamento agendamento = new Agendamento();
        agendamento.setData(dto.getData());
        agendamento.setHorario(dto.getHorario());
    
        agendamento.setUsuario(usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        // Verifica se o ID do profissional é nulo antes de buscar no repositório                    
        if (dto.getProfissionalId() != null) {
            agendamento.setProfissional(profissionalRepository.findById(dto.getProfissionalId())
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado")));
        }
            
        //agendamento.setProfissional(profissionalRepository.findById(dto.getProfissionalId())
          //  .orElseThrow(() -> new RuntimeException("Profissional não encontrado")));
        
        agendamento.setEspecialidade(especialidadeRepository.findById(dto.getEspecialidadeId())
            .orElseThrow(() -> new RuntimeException("Especialidade não encontrada")));
    
        agendamento.setProcedimento(dto.getProcedimento());
    
        return agendamento;
    }
    @GetMapping("/data")
    public List<Agendamento> findByData(@RequestParam LocalDate data) {
        return agendamentoService.findByData(data);
    }
    @GetMapping("/nome")
    public List<Agendamento> findByUsuarioNome(@RequestParam String nome) {
        return agendamentoService.findByUsuarioNome(nome);
    }
}