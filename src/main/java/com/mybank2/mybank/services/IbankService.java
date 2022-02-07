package com.mybank2.mybank.services;

import com.mybank2.mybank.dto.AccountStatement;
import com.mybank2.mybank.dto.TransactionStatementRequest;
import com.mybank2.mybank.entities.Deposit;
import com.mybank2.mybank.entities.Withdrawal;
import com.mybank2.mybank.utils.AccountNotFoundException;
import com.mybank2.mybank.utils.InsufficientFundsException;
import com.mybank2.mybank.utils.InvalidAmountException;

public interface IbankService {

    public Withdrawal withdrawal(TransactionStatementRequest transactionStatementRequest) throws InvalidAmountException,
            InsufficientFundsException, AccountNotFoundException;

    public Deposit deposit(TransactionStatementRequest transactionStatementRequest)
            throws InvalidAmountException, AccountNotFoundException;

    public AccountStatement getStatement(String accountNumber);

}
