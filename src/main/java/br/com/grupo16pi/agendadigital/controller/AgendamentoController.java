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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.grupo16pi.agendadigital.DTOs.AgendamentoRequestDTO;
import br.com.grupo16pi.agendadigital.model.Agendamento;
import br.com.grupo16pi.agendadigital.repository.EspecialidadeRepository;
import br.com.grupo16pi.agendadigital.repository.ProfissionalRepository;
import br.com.grupo16pi.agendadigital.repository.UsuarioRepository;
import br.com.grupo16pi.agendadigital.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping
    @Operation(summary = "Lista todos os agendamentos", description = "Retorna uma lista de todos os agendamentos")
    public List<Agendamento> findAll() {
        return agendamentoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca agendamento por ID", description = "Retorna um agendamento específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<Agendamento> findById(@PathVariable Long id) {
        try {
            Optional<Agendamento> agendamento = agendamentoService.findByIdWithRelations(id);
            return agendamento.map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erro ao buscar agendamento com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Cria um novo agendamento", description = "Cria um novo agendamento com base nos dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuário, especialidade ou profissional não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<Agendamento> save(@RequestBody @Valid AgendamentoRequestDTO dto) {
        try {
            Agendamento agendamento = toEntity(dto);
            Agendamento salvo = agendamentoService.save(agendamento);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            logger.error("Erro ao criar agendamento: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um agendamento", description = "Atualiza um agendamento existente com base no ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<Agendamento> update(@PathVariable Long id, @RequestBody Agendamento agendamento) {
        try {
            agendamento.setId(id);
            Agendamento atualizado = agendamentoService.save(agendamento);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            logger.error("Erro ao atualizar agendamento com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um agendamento", description = "Deleta um agendamento pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            agendamentoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar agendamento com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private Agendamento toEntity(AgendamentoRequestDTO dto) {
        Agendamento agendamento = new Agendamento();
        agendamento.setData(dto.getData());
        agendamento.setHorario(dto.getHorario());
    
        agendamento.setUsuario(usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
                    
        if (dto.getProfissionalId() != null) {
            agendamento.setProfissional(profissionalRepository.findById(dto.getProfissionalId())
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado")));
        }
        
        agendamento.setEspecialidade(especialidadeRepository.findById(dto.getEspecialidadeId())
            .orElseThrow(() -> new RuntimeException("Especialidade não encontrada")));
    
        agendamento.setProcedimento(dto.getProcedimento());
    
        return agendamento;
    }

    @GetMapping("/data")
    @Operation(summary = "Busca agendamentos por data", description = "Retorna uma lista de agendamentos para uma data específica")
    public List<Agendamento> findByData(@RequestParam LocalDate data) {
        return agendamentoService.findByData(data);
    }

    @GetMapping("/nome")
    @Operation(summary = "Busca agendamentos por nome do usuário", description = "Retorna uma lista de agendamentos pelo nome do usuário")
    public List<Agendamento> findByUsuarioNome(@RequestParam String nome) {
        return agendamentoService.findByUsuarioNome(nome);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Erro interno no controlador: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Erro interno no servidor: " + e.getMessage());
    }
}