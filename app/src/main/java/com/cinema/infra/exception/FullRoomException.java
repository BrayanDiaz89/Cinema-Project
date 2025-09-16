package com.cinema.infra.exception;

public class FullRoomException extends RuntimeException{
    public FullRoomException(String message){
        super(message);
    }
}
