package br.com.grupo16pi.agendadigital.enums;

import java.time.DayOfWeek;

public enum DiaSemanaEnum {

    DOMINGO(DayOfWeek.SUNDAY),
    SEGUNDA(DayOfWeek.MONDAY),
    TERCA(DayOfWeek.TUESDAY),
    QUARTA(DayOfWeek.WEDNESDAY),
    QUINTA(DayOfWeek.THURSDAY),
    SEXTA(DayOfWeek.FRIDAY),
    SABADO(DayOfWeek.SATURDAY);

    private final DayOfWeek dayOfWeek;

    DiaSemanaEnum(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }
}