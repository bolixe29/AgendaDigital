package br.com.grupo16pi.agendadigital.DTOs;

public class HorarioDTO {
    private int hour;
    private int minute;

    public HorarioDTO() {
    }

    public HorarioDTO(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "HorarioDTO{hour=" + hour + ", minute=" + minute + "}";
    }
}