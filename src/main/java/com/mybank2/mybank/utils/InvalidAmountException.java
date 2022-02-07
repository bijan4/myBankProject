package com.mybank2.mybank.utils;

public class InvalidAmountException extends Exception {
    public InvalidAmountException() {
        super();
    }

    public InvalidAmountException(double amount) {
        super("Montant invalide <= 0" + amount);
    }
}
