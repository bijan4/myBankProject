package com.mybank2.mybank.entities;

import javax.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Withdrawal extends Transaction {

    public Withdrawal(LocalDateTime dateOperation, double amount, String accountNumber) {
        super(dateOperation, amount, accountNumber);
    }

    public Withdrawal() {
    }
}
