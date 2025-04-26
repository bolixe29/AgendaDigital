package br.com.grupo16pi.agendadigital.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity // Marca a classe como uma entidade JPA (mapeada para uma tabela)
@Table(name = "profissionais") // Define o nome da tabela no banco de dados
public class Profissional {

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID gerado automaticamente pelo banco
    private Long id;

    @Column(nullable = false) // Nome obrigatório
    private String nome;

    @Column(nullable = false, unique = true) // CPF obrigatório e único
    private String cpf;

    @Column(nullable = false, unique = true) // Registro profissional (COREM ou CRM) obrigatório e único
    private String coremCrm;

    private String funcional; // Matrícula ou número funcional (não obrigatório)

    @ManyToOne // Relação muitos-para-um com a especialidade
    @JoinColumn(name = "especialidade_id") // Chave estrangeira
    private Especialidade especialidade;

    private boolean ativo; // Indica se o profissional está ativo no sistema

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

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }   
}