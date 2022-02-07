package com.mybank2.mybank.dto;

import java.util.List;

import com.mybank2.mybank.entities.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountStatement {
    double currentBalance;
    List<Transaction> transactionHistory;
}
