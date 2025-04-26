package br.com.grupo16pi.agendadigital.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity // Define que esta classe é uma entidade JPA (tabela no banco de dados)
@Table(name = "especialidades") // Nome da tabela no banco
public class Especialidade {

    @Id // Identifica o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID pelo banco (auto incremento)
    private Long id;

    @Column(nullable = false, unique = true) // Campo obrigatório e único (não pode ter dois nomes iguais)
    private String nome;

    @Column(nullable = false, unique = true) // Também obrigatório e único
    private String codEspecialidade;

    // Métodos getters e setters

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

    public String getCodEspecialidade() {
        return codEspecialidade;
    }

    public void setCodEspecialidade(String codEspecialidade) {
        this.codEspecialidade = codEspecialidade;
    }    
}
