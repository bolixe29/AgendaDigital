package br.com.grupo16pi.agendadigital.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupo16pi.agendadigital.DTOs.MatrizAgendaRequestDTO;
import br.com.grupo16pi.agendadigital.DTOs.MatrizAgendaResponseDTO;
import br.com.grupo16pi.agendadigital.model.Especialidade;
import br.com.grupo16pi.agendadigital.model.MatrizAgenda;
import br.com.grupo16pi.agendadigital.repository.EspecialidadeRepository;
import br.com.grupo16pi.agendadigital.repository.MatrizAgendaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/matrizes-agenda")
public class MatrizAgendaController {

    private final MatrizAgendaRepository matrizAgendaRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public MatrizAgendaController(MatrizAgendaRepository matrizAgendaRepository, EspecialidadeRepository especialidadeRepository) {
        this.matrizAgendaRepository = matrizAgendaRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @GetMapping
    public List<MatrizAgendaResponseDTO> listarTodos() {
        return matrizAgendaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatrizAgendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return matrizAgendaRepository.findById(id)
                .map(agenda -> ResponseEntity.ok(toResponseDTO(agenda)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MatrizAgendaResponseDTO> criar(@RequestBody @Valid MatrizAgendaRequestDTO dto) {
        Especialidade especialidade = especialidadeRepository.findById(dto.getEspecialidadeId())
            .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        MatrizAgenda nova = new MatrizAgenda();
        nova.setEspecialidade(especialidade);
        nova.setDiaSemana(dto.getDiaSemana());
        nova.setHorarioInicio(dto.getHorarioInicio());
        nova.setHorarioFim(dto.getHorarioFim());
        nova.setDisponivel(dto.getDisponivel());

        MatrizAgenda salva = matrizAgendaRepository.save(nova);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatrizAgendaResponseDTO> atualizar(@PathVariable Long id,
        @RequestBody @Valid MatrizAgendaRequestDTO dto) {

        return matrizAgendaRepository.findById(id).map(existing -> {
            Especialidade especialidade = especialidadeRepository.findById(dto.getEspecialidadeId())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

            existing.setEspecialidade(especialidade);
            existing.setDiaSemana(dto.getDiaSemana());
            existing.setHorarioInicio(dto.getHorarioInicio());
            existing.setHorarioFim(dto.getHorarioFim());
            existing.setDisponivel(dto.getDisponivel());

            matrizAgendaRepository.save(existing);
            return ResponseEntity.ok(toResponseDTO(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (matrizAgendaRepository.existsById(id)) {
            matrizAgendaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private MatrizAgendaResponseDTO toResponseDTO(MatrizAgenda entity) {
        MatrizAgendaResponseDTO dto = new MatrizAgendaResponseDTO();
        dto.setId(entity.getId());
        dto.setNomeEspecialidade(entity.getEspecialidade().getNome());
        dto.setDiaSemana(entity.getDiaSemana());
        dto.setHorarioInicio(entity.getHorarioInicio());
        dto.setHorarioFim(entity.getHorarioFim());
        dto.setDisponivel(entity.isDisponivel());
        return dto;
    }
}