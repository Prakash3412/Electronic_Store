package com.lcwd.electronics.store.exception;

public class BadApiRequest extends RuntimeException {
    public BadApiRequest(String message)
    {
        super(message);
    }
    public BadApiRequest(){
        super("Bed Request!!");
    }

}
