package br.com.grupo16pi.agendadigital.DTOs;


import jakarta.validation.constraints.NotBlank;

public class ProfissionalRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "O COREM/CRM é obrigatório")
    private String coremCrm;

    private String funcional;
    private Long especialidadeId;
    private boolean ativo;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCoremCrm() {
        return coremCrm;
    }
    public void setCoremCrm(String coremCrm) {
        this.coremCrm = coremCrm;
    }
    public String getFuncional() {
        return funcional;
    }
    public void setFuncional(String funcional) {
        this.funcional = funcional;
    }
    public Long getEspecialidadeId() {
        return especialidadeId;
    }
    public void setEspecialidadeId(Long especialidadeId) {
        this.especialidadeId = especialidadeId;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
