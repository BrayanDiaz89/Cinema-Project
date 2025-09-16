package com.cinema.model.entitie;

import com.cinema.model.dto.TheaterRegistrationData;

public class Theater {
    //Data by theater
    private String name;
    private Room[] rooms;

    public String getName() {
        return name;
    }
    public Room[] getRooms() {
        return rooms;
    }

    public Theater(TheaterRegistrationData dataRegistration){
        this.name = dataRegistration.name();
        this.rooms = new Room[dataRegistration.roomsQuantity()];

        for(int i = 0; i < dataRegistration.roomsQuantity(); i++){
            rooms[i] = new Room("Sala " + ((int) i + 1), dataRegistration.rows(),
                    dataRegistration.columns(),
                    dataRegistration.nameEvent());
        }
    }

}
