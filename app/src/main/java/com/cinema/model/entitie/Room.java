package com.cinema.model.entitie;

public class Room {

    private String name;
    private Seat[][] seats;
    private String nameEvent;

    public Room(String name, Integer rows, Integer columns, String nameEvent){
        this.name = name;
        seats = new Seat[rows][columns];
        this.nameEvent = nameEvent;

        for(int i = 0; i < rows; i++){
            char rowLetter = (char) ('A' + i);
            for(int j = 0; j < columns; j++){
                String nameSeat = rowLetter + String.valueOf(j + 1);
                seats[i][j] = new Seat(nameSeat);
            }
        }
    }

    public String getName() {
        return name;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public Integer getTotalNumberOfSeats(){
        return seats.length * seats[0].length;
    }

    @Override
    public String toString() {
        return String.format("""
                Nombre de la sala: %s
                Total de sillas de la sala: %d
                Evento a presentar: %s
                """, name, getTotalNumberOfSeats(), nameEvent);
    }
}
