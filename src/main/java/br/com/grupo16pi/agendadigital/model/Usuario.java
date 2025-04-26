package br.com.grupo16pi.agendadigital.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity // Marca a classe como uma entidade JPA
@Table(name = "usuarios") // Define o nome da tabela no banco de dados
public class Usuario {

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID gerado automaticamente pelo banco
    private Long id;

    @Column(nullable = false) // Nome obrigatório
    private String nome;

    @Column(nullable = false) // Data de nascimento obrigatória
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true) // CPF obrigatório e único
    private String cpf;

    @Column(nullable = false, unique = true) // Número do SUS obrigatório e único
    private String numeroSus;

    private String telefone;     // Telefone (opcional)
    private String logradouro;   // Rua ou avenida
    private String numero;       // Número da residência
    private String cep;          // Código postal
    private String bairro;       // Bairro do usuário
    private String cidade;       // Cidade
    private String uf;           // Estado (UF)

    // Getters e setters...

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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}