package com.cinema.infra.exception;

public class FreeSeatException extends RuntimeException{
    public FreeSeatException(String message){
        super(message);
    }
}
