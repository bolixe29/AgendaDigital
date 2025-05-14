
package br.com.grupo16pi.agendadigital.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupo16pi.agendadigital.enums.DiaSemanaEnum;
import br.com.grupo16pi.agendadigital.model.Agendamento;
import br.com.grupo16pi.agendadigital.model.MatrizAgenda;
import br.com.grupo16pi.agendadigital.repository.AgendamentoRepository;
import br.com.grupo16pi.agendadigital.repository.MatrizAgendaRepository;

@Service
public class DisponibilidadeService {

    @Autowired
    private MatrizAgendaRepository matrizAgendaRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<LocalDate> buscarDiasComDisponibilidade(Long especialidadeId, int ano, int mes) {
        List<MatrizAgenda> matriz = matrizAgendaRepository.findByEspecialidadeIdAndDisponivelTrue(especialidadeId);

        if (matriz.isEmpty()) return List.of();

        Set<DayOfWeek> diasAtivos = matriz.stream()
            .map(MatrizAgenda::getDiaSemana)
            .map(DiaSemanaEnum::getDayOfWeek)
            .collect(Collectors.toSet());

        YearMonth ym = YearMonth.of(ano, mes);
        return IntStream.rangeClosed(1, ym.lengthOfMonth())
            .mapToObj(day -> LocalDate.of(ano, mes, day))
            .filter(data -> diasAtivos.contains(data.getDayOfWeek()))
            .collect(Collectors.toList());
    }

    public List<LocalTime> buscarHorariosDisponiveis(Long especialidadeId, LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();

        List<MatrizAgenda> matriz = matrizAgendaRepository.findByEspecialidadeIdAndDisponivelTrue(especialidadeId);

        Optional<MatrizAgenda> agendaDoDia = matriz.stream()
            .filter(m -> m.getDiaSemana().getDayOfWeek().equals(dia))
            .findFirst();

        if (agendaDoDia.isEmpty()) return List.of();

        LocalTime inicio = agendaDoDia.get().getHorarioInicio();
        LocalTime fim = agendaDoDia.get().getHorarioFim();

        List<LocalTime> todosHorarios = new ArrayList<>();
        for (LocalTime t = inicio; t.isBefore(fim); t = t.plusMinutes(20)) {
            todosHorarios.add(t);
        }

        List<Agendamento> agendamentos = agendamentoRepository.findByEspecialidadeIdAndData(especialidadeId, data);       
        Set<LocalTime> ocupados = agendamentos.stream()
            .map(Agendamento::getHorario)
            .collect(Collectors.toSet());

        return todosHorarios.stream()
            .filter(h -> !ocupados.contains(h))
            .collect(Collectors.toList());
    }
}
