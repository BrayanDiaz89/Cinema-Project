package com.cinema.service;

import com.cinema.infra.exception.BusySeatException;
import com.cinema.infra.exception.FreeSeatException;
import com.cinema.infra.exception.InvalidSeatException;
import com.cinema.model.dto.StateRoomResponse;
import com.cinema.model.entitie.Room;
import com.cinema.model.entitie.Seat;
import com.cinema.model.entitie.Theater;
import com.cinema.model.enums.StateSeat;


public class TheaterService {

    private Integer rows;
    private Integer columns;
    private Seat[][] seats;

    public Room[] getRoomsByTheater(Theater theater){
        return theater.getRooms();
    }

    public StateSeat[][] getStateSeats(Room room){
        rows = room.getSeats().length;
        columns = room.getSeats()[0].length;
        StateSeat[][] stateSeats = new StateSeat[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                stateSeats[i][j] = room.getSeats()[i][j].getState();
            }
        }
        return stateSeats;
    }


    public boolean reserveASeat(Room room, Integer row, Integer column){
        rows = room.getSeats().length;
        columns = room.getSeats()[0].length;
        seats = room.getSeats();
        row--;
        column--;
        if(row < 0 || row > rows){
            throw new InvalidSeatException("No se debe ingresar valores negativos.");
        }
        if(column < 0 || column > columns){
            throw new InvalidSeatException("No existe la silla mencionada en la sala.");
        }
        if(seats[row][column].isSeatFree()){
            seats[row][column].occupySeat();
            return true;
        } else {
            throw new BusySeatException("El asiento se encuentra ocupado, no fue posible reservarlo.");
        }
    }

    public boolean cancelSeatReservation(Room room, Integer row, Integer column){
        rows = room.getSeats().length;
        columns = room.getSeats()[0].length;
        seats = room.getSeats();
        row--;
        column--;
        if(row < 0 || row > rows){
            throw new InvalidSeatException("No se debe ingresar valores negativos.");
        }
        if(column < 0 || column > columns){
            throw new InvalidSeatException("No existe la silla mencionada en la sala.");
        }
        if(!seats[row][column].isSeatFree()){
            seats[row][column].freeSeat();
            return true;
        } else {
            throw new FreeSeatException("Error cancelando la reserva, la silla mencionada no ha sido reservada antes.");
        }
    }

    public StateRoomResponse getRoomStatistics(Room room){
        Integer numberOfFreeSeats = 0;
        Integer numberOfSeatsOccupied = 0;
        Double percentageOfRoomOccupancy;
        Integer totalNumberOfSeats;
        rows = room.getSeats().length;
        columns = room.getSeats()[0].length;
        seats = room.getSeats();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(seats[i][j].getState() == StateSeat.AVAILABLE){
                    numberOfFreeSeats++;
                } else {
                    numberOfSeatsOccupied++;
                }
            }
        }
        totalNumberOfSeats = numberOfFreeSeats + numberOfSeatsOccupied;
        percentageOfRoomOccupancy = totalNumberOfSeats == 0 ? 0.0 : ((double) numberOfSeatsOccupied / totalNumberOfSeats) * 100;
        return new StateRoomResponse(numberOfFreeSeats, numberOfSeatsOccupied, percentageOfRoomOccupancy);
    }

}
