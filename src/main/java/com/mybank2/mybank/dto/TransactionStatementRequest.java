package com.mybank2.mybank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionStatementRequest {
    
    private String accountNumber;
    private double amount;
}
