package br.com.grupo16pi.agendadigital.DTOs;

import br.com.grupo16pi.agendadigital.enums.UfEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioUpdateDTO {

    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String numeroSus;
    private String telefone;
    private String logradouro;
    private String numero;
    private String cep;
    private String bairro;
    private String cidade;
    private UfEnum uf;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNumeroSus() {
        return numeroSus;
    }
    public void setNumeroSus(String numeroSus) {
        this.numeroSus = numeroSus;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public UfEnum getUf() {
        return uf;
    }
    public void setUf(UfEnum uf) {
        this.uf = uf;
    }
}
