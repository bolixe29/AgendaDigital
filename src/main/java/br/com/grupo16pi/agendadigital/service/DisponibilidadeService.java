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

import br.com.grupo16pi.agendadigital.DTOs.HorarioDTO;
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
        System.out.println("MatrizAgenda para dias disponíveis: " + matriz);

        if (matriz.isEmpty()) return List.of();

        // Pega os dias da semana que têm entradas na matriz
        Set<DayOfWeek> diasAtivos = matriz.stream()
            .map(MatrizAgenda::getDiaSemana)
            .map(DiaSemanaEnum::getDayOfWeek)
            .collect(Collectors.toSet());
        System.out.println("Dias ativos: " + diasAtivos);

        YearMonth ym = YearMonth.of(ano, mes);
        return IntStream.rangeClosed(1, ym.lengthOfMonth())
            .mapToObj(day -> LocalDate.of(ano, mes, day))
            .filter(data -> {
                // Só inclui o dia se ele tiver horários disponíveis
                DayOfWeek dia = data.getDayOfWeek();
                if (!diasAtivos.contains(dia)) return false;
                // Verifica se há horários disponíveis pra esse dia
                List<HorarioDTO> horarios = buscarHorariosDisponiveis(especialidadeId, data);
                return !horarios.isEmpty();
            })
            .collect(Collectors.toList());
    }

    public List<HorarioDTO> buscarHorariosDisponiveis(Long especialidadeId, LocalDate data) {
        System.out.println("Buscando horários para especialidadeId: " + especialidadeId + ", data: " + data);
        DayOfWeek dia = data.getDayOfWeek();
        System.out.println("Dia da semana: " + dia);

        List<MatrizAgenda> matriz = matrizAgendaRepository.findByEspecialidadeIdAndDisponivelTrue(especialidadeId);
        System.out.println("MatrizAgenda encontrada: " + matriz);

        Optional<MatrizAgenda> agendaDoDia = matriz.stream()
            .filter(m -> m.getDiaSemana().getDayOfWeek().equals(dia))
            .findFirst();
        System.out.println("Agenda para o dia " + dia + ": " + (agendaDoDia.isPresent() ? agendaDoDia.get() : "Nenhuma"));

        if (agendaDoDia.isEmpty()) return List.of();

        LocalTime inicio = agendaDoDia.get().getHorarioInicio();
        LocalTime fim = agendaDoDia.get().getHorarioFim();
        System.out.println("Horário início: " + inicio + ", Horário fim: " + fim);

        List<HorarioDTO> todosHorarios = new ArrayList<>();
        for (LocalTime t = inicio; !t.isAfter(fim); t = t.plusMinutes(20)) {
            todosHorarios.add(new HorarioDTO(t.getHour(), t.getMinute()));
        }
        System.out.println("Todos os horários gerados: " + todosHorarios);

        List<Agendamento> agendamentos = agendamentoRepository.findByEspecialidadeIdAndData(especialidadeId, data);
        Set<LocalTime> ocupados = agendamentos.stream()
            .map(Agendamento::getHorario)
            .collect(Collectors.toSet());
        System.out.println("Horários ocupados: " + ocupados);

        List<HorarioDTO> horariosDisponiveis = todosHorarios.stream()
            .filter(h -> !ocupados.contains(LocalTime.of(h.getHour(), h.getMinute())))
            .collect(Collectors.toList());
        System.out.println("Horários disponíveis após filtro: " + horariosDisponiveis);

        return horariosDisponiveis;
    }
}