package br.com.grupo16pi.agendadigital.DTOs;

import java.time.LocalTime;

import br.com.grupo16pi.agendadigital.enums.DiaSemanaEnum;
import jakarta.validation.constraints.NotNull;

public class MatrizAgendaRequestDTO {
    @NotNull
    private Long especialidadeId;

    @NotNull
    private DiaSemanaEnum diaSemana;

    @NotNull
    private LocalTime horarioInicio;

    @NotNull
    private LocalTime horarioFim;

    @NotNull
    private Boolean disponivel;


    // Getters e Setters

    public Long getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(Long especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public DiaSemanaEnum getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemanaEnum diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
}