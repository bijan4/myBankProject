package com.mybank2.mybank.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public abstract class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private Long number;

    private LocalDateTime dateOperation;
    private double amount;

    private String accountNumber;

    public Transaction(LocalDateTime dateOperation, double amount, String accountNumber) {
        this.dateOperation = dateOperation;
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public Transaction() {
        super();
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public LocalDateTime getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation = dateOperation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
