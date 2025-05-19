package br.com.grupo16pi.agendadigital.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "DTO para criação de um agendamento")
public class AgendamentoRequestDTO {

    @NotNull(message = "A data é obrigatória")
    @Schema(description = "Data do agendamento (formato yyyy-MM-dd)", example = "2025-05-19", required = true)
    private LocalDate data;

    @NotNull(message = "O horário é obrigatório")
    @Schema(description = "Horário do agendamento (formato HH:mm:ss)", example = "15:00:00", required = true)
    private LocalTime horario;

    @NotNull(message = "O ID do usuário é obrigatório")
    @Schema(description = "ID do usuário", example = "8", required = true)
    private Long usuarioId;

    @NotNull(message = "A especialidade é obrigatória")
    @Schema(description = "ID da especialidade", example = "11", required = true)
    private Long especialidadeId;

    @Schema(description = "Procedimento (opcional)", example = "CONSULTA", required = false)
    private String procedimento;

    @Schema(description = "ID do profissional (opcional)", example = "1", required = false)
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

    public Long getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(Long especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }
}