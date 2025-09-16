package com.cinema.model.dto;

public record TheaterRegistrationData(
        String name,
        Integer roomsQuantity,
        Integer rows,
        Integer columns,
        String nameEvent
) {


}
