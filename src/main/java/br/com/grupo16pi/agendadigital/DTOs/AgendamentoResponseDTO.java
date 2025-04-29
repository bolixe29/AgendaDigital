package br.com.grupo16pi.agendadigital.DTOs;


import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoResponseDTO {

    private Long id;
    private LocalDate data;
    private LocalTime horario;
    private String nomeUsuario;
    private String nomeProfissional;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    public String getNomeProfissional() {
        return nomeProfissional;
    }
    public void setNomeProfissional(String nomeProfissional) {
        this.nomeProfissional = nomeProfissional;
    }

    // Getters e Setters
}
