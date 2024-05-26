package com.nsh.customerservice.exceptionhandler.exceptions;

public class CustomerAlreadyExist extends RuntimeException{
    public CustomerAlreadyExist() {
        super();
    }

    public CustomerAlreadyExist(String message) {
        super(message);
    }
}
