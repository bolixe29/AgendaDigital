package br.com.grupo16pi.agendadigital.DTOs;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoRequestDTO {

    @NotNull(message = "A data é obrigatória")
    private LocalDate data;

    @NotNull(message = "O horário é obrigatório")
    private LocalTime horario;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "O ID do profissional é obrigatório")
    private Long profissionalId;

    // Getters e Setters

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

}
