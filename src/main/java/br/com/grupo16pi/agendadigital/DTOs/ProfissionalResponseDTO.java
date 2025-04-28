package br.com.grupo16pi.agendadigital.DTOs;

public class ProfissionalResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String coremCrm;
    private boolean ativo;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }    
}
