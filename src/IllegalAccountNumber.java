package model;

public class IllegalAccountNumber extends RuntimeException {
    public IllegalAccountNumber() {
        super("Illegal account number");
    }
}
