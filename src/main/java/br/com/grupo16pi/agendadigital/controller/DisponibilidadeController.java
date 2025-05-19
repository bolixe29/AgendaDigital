package br.com.grupo16pi.agendadigital.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupo16pi.agendadigital.DTOs.HorarioDTO;
import br.com.grupo16pi.agendadigital.service.DisponibilidadeService; // Classe HorarioDTO já definida no service ajustado


@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @GetMapping
    public ResponseEntity<List<HorarioDTO>> getHorariosDisponiveis(
            @RequestParam Long especialidadeId,
            @RequestParam String data) {
        try {
            // Converte a string da data para LocalDate
            LocalDate localDate = LocalDate.parse(data);
            System.out.println("Recebido especialidadeId: " + especialidadeId + ", data: " + localDate);

            // Chama o serviço e retorna os horários
            List<HorarioDTO> horarios = disponibilidadeService.buscarHorariosDisponiveis(especialidadeId, localDate);
            System.out.println("Horários retornados: " + horarios);
            return ResponseEntity.ok(horarios);
        } catch (Exception e) {
            System.err.println("Erro ao processar a data: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/dias-disponiveis")
    public List<LocalDate> getDiasDisponiveis(
            @RequestParam Long especialidadeId,
            @RequestParam int ano,
            @RequestParam int mes) {
        return disponibilidadeService.buscarDiasComDisponibilidade(especialidadeId, ano, mes);
    }
}