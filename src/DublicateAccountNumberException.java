package model;

import java.io.IOException;

public class DublicateAccountNumberException extends IOException {

    public DublicateAccountNumberException() {
        super();
    }

    public DublicateAccountNumberException(String message) {
        super(message);
    }

    public DublicateAccountNumberException(String message, Throwable cause) {
        super(message, cause);
    }

}
