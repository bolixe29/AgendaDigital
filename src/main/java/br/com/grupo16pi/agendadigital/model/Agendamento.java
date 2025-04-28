package br.com.grupo16pi.agendadigital.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "agendamentos") // Define a tabela no banco de dados com o nome "agendamentos"
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente (auto incremento)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Chave estrangeira para o usuário
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false) // Chave estrangeira para o profissional
    private Profissional profissional;

    @Column(nullable = false) // Campo obrigatório para o nome do procedimento
    private String procedimento;

    @Column(nullable = false) // Campo obrigatório para a data do agendamento
    private LocalDate data;

    @Column(nullable = false) // Campo obrigatório para o horário do agendamento
    private LocalTime horario;

    // Getters e setters (acessores e modificadores)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
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
}
