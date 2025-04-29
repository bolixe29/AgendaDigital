package br.com.grupo16pi.agendadigital.DTOs;

import jakarta.validation.constraints.NotBlank;

public class EspecialidadeRequestDTO {

    @NotBlank(message = "O nome da especialidade é obrigatório")
    private String nome;

    @NotBlank(message = "O código da especialidade é obrigatório")
    private String codEspecialidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodEspecialidade() {
        return codEspecialidade;
    }

    public void setCodEspecialidade(String codEspecialidade) {
        this.codEspecialidade = codEspecialidade;
    }    
}
