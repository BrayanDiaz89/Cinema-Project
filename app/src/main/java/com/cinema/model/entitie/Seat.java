package com.cinema.model.entitie;

import com.cinema.model.enums.StateSeat;

public class Seat {
    private String nameSeat;
    private StateSeat state;

    public Seat(String nameSeat){
        this.nameSeat = nameSeat;
        state = StateSeat.AVAILABLE;
    }

    public void occupySeat(){
        state = StateSeat.BUSY;
    }

    public void freeSeat(){
        state = StateSeat.AVAILABLE;
    }

    public boolean isSeatFree(){
        return state == StateSeat.AVAILABLE;
    }

    public StateSeat getState() {
        return state;
    }

    public String getNameSeat() {
        return nameSeat;
    }
}
