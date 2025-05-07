package br.com.grupo16pi.agendadigital.DTOs;

import java.time.LocalTime;

import br.com.grupo16pi.agendadigital.enums.DiaSemanaEnum;

public class MatrizAgendaResponseDTO {

    private Long id;
    private String nomeEspecialidade;
    private DiaSemanaEnum diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private boolean disponivel;
    
    // Getters e Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }
    public void setNomeEspecialidade(String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
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
    public boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}