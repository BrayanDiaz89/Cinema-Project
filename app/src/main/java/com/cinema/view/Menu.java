package com.cinema.view;

import com.cinema.infra.exception.BusySeatException;
import com.cinema.infra.exception.FreeSeatException;
import com.cinema.infra.exception.InvalidRoomException;
import com.cinema.infra.exception.InvalidSeatException;
import com.cinema.model.dto.TheaterRegistrationData;
import com.cinema.model.entitie.Room;
import com.cinema.model.entitie.Theater;
import com.cinema.service.TheaterService;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    private TheaterService service = new TheaterService();
    private boolean exit = false;
    public Menu() {}

    public void menuApp(Scanner keyboard){
        var theater = createTheater(keyboard);
        programTheaterFlowInteraction(keyboard, theater);
    }

    public Theater createTheater(Scanner keyboard){
        System.out.println("¡Bienvenido al menú de creación de teatros!");
        System.out.print("Digite el nombre del teatro: ");
        String nameTheater = keyboard.nextLine();
        System.out.printf("Digite la cantidad de salas para %s: ", nameTheater);
        Integer rooms = keyboard.nextInt();
        //keyboard.nextLine();
        System.out.print("Digite la cantidad de filas de asientos, para las salas: ");
        Integer rows = keyboard.nextInt();
        //keyboard.nextLine();
        System.out.print("Digite la cantidad de columnas de asientos, para las salas: ");
        Integer columns = keyboard.nextInt();
        keyboard.nextLine();
        System.out.print("Digite el nombre del evento para las salas: ");
        String nameEvent = keyboard.nextLine();

        var theater = new Theater(new TheaterRegistrationData(nameTheater, rooms, rows, columns, nameEvent));

        return theater;
    }

    public void programTheaterFlowInteraction(Scanner keyboard, Theater theater){
        Integer[] positionSeat;
        while (!exit) {
            String menuApp = String.format("""
            \nBienvenido al teatro %s.
            1) Visualizar salas del teatro.
            2) Visualizar mapa de asientos de una sala (Libres y Ocupados).
            3) Reservar un asiento de una sala.
            4) Cancelar reserva de un asiento en una sala.
            5) Consultar estadísticas de una sala.
            6) Salir.
            """, theater.getName());
            System.out.println(menuApp);
            Integer option = keyboard.nextInt();
            Room room;
            Integer row;
            Integer column;
            switch (option) {
                case 1:
                    var rooms = service.getRoomsByTheater(theater);
                    System.out.println(Arrays.toString(rooms));
                    break;
                case 2:
                    try {
                        room = selectARoom(keyboard, theater);
                        var stateSeats = service.getStateSeats(room);
                        System.out.println(Arrays.deepToString(stateSeats));
                    } catch (InvalidRoomException ex){
                        System.out.println("Error: "+ ex.getMessage());
                    }
                    break;
                case 3:
                    try{
                        room = selectARoom(keyboard, theater);
                        positionSeat = getPositionsSeat(keyboard, room);
                        row = positionSeat[0];
                        column = positionSeat[1];
                        String successfulReservation = service.reserveASeat(room, row, column) ? "¡Reserva exitosa!" : "Reserva denegada.";
                        System.out.println(successfulReservation);
                    } catch (InvalidSeatException | BusySeatException | InvalidRoomException ex){
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                case 4:
                    try {
                        room = selectARoom(keyboard, theater);
                        positionSeat = getPositionsSeat(keyboard, room);
                        row = positionSeat[0];
                        column = positionSeat[1];
                        String successfulReservationCancellation = service.cancelSeatReservation(room, row, column)
                                ? "Reserva cancelada con éxito." : "No pudo ser cancelada la reserva";
                        System.out.println(successfulReservationCancellation);
                    } catch (InvalidSeatException | FreeSeatException | InvalidRoomException ex){
                        System.out.println("Error: "+ ex.getMessage());
                    }
                    break;
                case 5:
                    try{
                        room = selectARoom(keyboard, theater);
                        var stateRoomStatistics = service.getRoomStatistics(room);
                        System.out.println(stateRoomStatistics);
                    } catch (InvalidRoomException ex){
                        System.out.println("Error: "+ ex.getMessage());
                    }

                    break;
                case 6:
                    System.out.println("Gracias por su visita. Saliendo el programa...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    public Room selectARoom(Scanner keyboard, Theater theater){
        System.out.println("Digite el número de la sala: ");
        Integer numberRoom = keyboard.nextInt();
        keyboard.nextLine();
        if(numberRoom <= 0 || numberRoom > theater.getRooms().length){
            throw new InvalidRoomException("Error, sala no válida.");
        }
        return theater.getRooms()[numberRoom-1];
    }

    public Integer[] getPositionsSeat(Scanner keyboard, Room room){
        Integer[] positionSeat = new Integer[2];
        System.out.println("Digite la fila en la que está ubicado el asiento: ");
        Integer row = keyboard.nextInt();
        keyboard.nextLine();
        if(row <= 0 || row > room.getSeats().length){
            throw new InvalidSeatException("Asiento no disponible, desbordamiento en capacidad de sala.");
        }
        System.out.println("Digite la columna en la que está ubicado el asiento: ");
        Integer column = keyboard.nextInt();
        keyboard.nextLine();
        if(column <= 0 || column > room.getSeats()[0].length){
            throw new InvalidSeatException("Asiento no disponible, desbordamiento en capacidad de sala.");
        }
        positionSeat[0] = row;
        positionSeat[1] = column;

        return positionSeat;
    }
}
