package br.com.grupo16pi.agendadigital.model;

import java.time.LocalTime;

import br.com.grupo16pi.agendadigital.enums.DiaSemanaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity // Marca esta classe como uma entidade JPA (mapeada para uma tabela)
@Table(name = "matriz_agenda") // Define o nome da tabela no banco
public class MatrizAgenda {

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    private Long id;

    @ManyToOne // Relação muitos-para-um com Especialidade
    @JoinColumn(name = "especialidade_id", nullable = false) // Chave estrangeira que referencia uma especialidade
    private Especialidade especialidade;
    
    @Enumerated(EnumType.STRING) // Salva o enum como texto no banco e dia da semana (ex: 1 = Domingo, 2 = Segunda, etc.) 
    @Column(nullable = false) // Não nulo
    private DiaSemanaEnum diaSemana;

    @Column(nullable = false) // Hora de início da disponibilidade
    private LocalTime horarioInicio;

    @Column(nullable = false) // Hora de término da disponibilidade
    private LocalTime horarioFim;

    @Column(nullable = false) // Se a agenda está disponível ou não
    private boolean disponivel;

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public DiaSemanaEnum getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemanaEnum diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
