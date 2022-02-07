package com.mybank2.mybank.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;

@Entity
public class Deposit extends Transaction {

    public Deposit(LocalDateTime dateOperation, double amount, String accountNumber) {
        super(dateOperation, amount, accountNumber);
    }

    public Deposit() {
        super();
    }
}
