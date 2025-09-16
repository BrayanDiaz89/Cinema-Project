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
        rows = getLenghtRowsAndColumnsFromRoom(room)[0];
        columns = getLenghtRowsAndColumnsFromRoom(room)[1];
        StateSeat[][] stateSeats = new StateSeat[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                stateSeats[i][j] = room.getSeats()[i][j].getState();
            }
        }
        return stateSeats;
    }

    public Integer[] getLenghtRowsAndColumnsFromRoom(Room room){
        Integer[] rowsAndColumnsFromRoom = new Integer[2];
        rowsAndColumnsFromRoom[0] = room.getSeats().length; //Obtengo filas
        rowsAndColumnsFromRoom[1] = room.getSeats()[0].length; //Obtengo columnas
        return rowsAndColumnsFromRoom;
    }

    public boolean reserveASeat(Room room, Integer row, Integer column){
        rows = getLenghtRowsAndColumnsFromRoom(room)[0];
        columns = getLenghtRowsAndColumnsFromRoom(room)[1];
        seats = room.getSeats();
        row--;
        column--;
        if(row < 0 || column < 0){
            throw new InvalidSeatException("No se debe ingresar valores negativos.");
        }
        if(row > rows || column > columns){
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
        rows = getLenghtRowsAndColumnsFromRoom(room)[0];
        columns = getLenghtRowsAndColumnsFromRoom(room)[1];
        seats = room.getSeats();
        if(row < 0 || column < 0){
            throw new InvalidSeatException("No se debe ingresar valores negativos.");
        }
        if(row > rows || column > columns){
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
        rows = getLenghtRowsAndColumnsFromRoom(room)[0];
        columns = getLenghtRowsAndColumnsFromRoom(room)[1];
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
