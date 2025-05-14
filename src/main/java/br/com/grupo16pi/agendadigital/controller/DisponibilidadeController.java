package br.com.grupo16pi.agendadigital.controller;

import br.com.grupo16pi.agendadigital.service.DisponibilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @GetMapping
    public List<LocalTime> getHorariosDisponiveis(
        @RequestParam Long especialidadeId,
        @RequestParam LocalDate data) {

        return disponibilidadeService.buscarHorariosDisponiveis(especialidadeId, data);
    }
    @GetMapping("/dias-disponiveis")
    public List<LocalDate> getDiasDisponiveis(
    @RequestParam Long especialidadeId,
    @RequestParam int ano,
    @RequestParam int mes) {

    return disponibilidadeService.buscarDiasComDisponibilidade(especialidadeId, ano, mes);
    }

}
