package com.cinema.infra.exception;

public class BusySeatException extends RuntimeException{
    public BusySeatException(String message){
        super(message);
    }
}

