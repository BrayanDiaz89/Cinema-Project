package com.cinema.model.dto;

public record StateRoomResponse(
        Integer numberOfFreeSeats,
        Integer numberOfSeatsOccupied,
        Double percentageOfRoomOccupancy
) {
}
