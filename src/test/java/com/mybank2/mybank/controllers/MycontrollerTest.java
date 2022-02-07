package com.mybank2.mybank.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.mybank2.mybank.dto.AccountStatement;
import com.mybank2.mybank.dto.TransactionStatementRequest;
import com.mybank2.mybank.entities.Deposit;
import com.mybank2.mybank.entities.Transaction;
import com.mybank2.mybank.entities.Withdrawal;
import com.mybank2.mybank.services.IbankService;
import com.mybank2.mybank.utils.AccountNotFoundException;
import com.mybank2.mybank.utils.InsufficientFundsException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class MycontrollerTest {

    @Mock
    IbankService myBankService;

    @InjectMocks
    private MybankController myBankController;

    @Test
    public void depositTestOk() throws Exception {
        TransactionStatementRequest tt = new TransactionStatementRequest();
        tt.setAmount(123);
        tt.setAccountNumber("codeAccount");

        Deposit depo = new Deposit(LocalDateTime.now(), 123, "codeAccount");

        when(myBankService.deposit(tt)).thenReturn(depo);

        ResponseEntity<?> response = myBankController.deposit(tt);
        ResponseEntity<?> expected = new ResponseEntity<>("dépôt effectué avec succès",
                HttpStatus.OK);
        assertEquals(response, expected);
    }

    @Test
    public void depositTestKoAccountNotFound() throws Exception {
        TransactionStatementRequest tt = new TransactionStatementRequest();
        tt.setAmount(123);
        tt.setAccountNumber("codeAccount");
        when(myBankService.deposit(tt)).thenThrow(new AccountNotFoundException());
        assertThrows(AccountNotFoundException.class,
                () -> myBankController.deposit(tt));
    }

    @Test
    public void withdrawalTestOk() throws Exception {
        TransactionStatementRequest tt = new TransactionStatementRequest();
        tt.setAmount(123);
        tt.setAccountNumber("codeAccount");

        Withdrawal withdrawal = new Withdrawal(LocalDateTime.now(), 123, "codeAccount");

        when(myBankService.withdrawal(tt)).thenReturn(withdrawal);

        ResponseEntity<?> response = myBankController.withdrawal(tt);
        ResponseEntity<?> expected = new ResponseEntity<>("Retrait effectué avec succès",
                HttpStatus.OK);
        assertEquals(response, expected);
    }

    @Test
    public void withdrawalTestInsufficientFundsException() throws Exception {
        TransactionStatementRequest tt = new TransactionStatementRequest();
        tt.setAmount(123);
        tt.setAccountNumber("codeAccount");
        when(myBankService.withdrawal(tt)).thenThrow(new InsufficientFundsException());
        assertThrows(InsufficientFundsException.class,
                () -> myBankController.withdrawal(tt));
    }

    @Test
    public void transactionsListTestOk() throws Exception {
        Transaction deposit = new Deposit(LocalDateTime.now(), 10, "code1");
        Transaction whithdrawal = new Withdrawal(LocalDateTime.now(), 5, "code1");

        List<Transaction> transactions = Arrays.asList(deposit, whithdrawal);

        AccountStatement as = new AccountStatement();
        as.setCurrentBalance(105);
        as.setTransactionHistory(transactions);
        when(myBankService.getStatement("code1")).thenReturn(as);
        AccountStatement response = myBankController.getStatement("code1");
        AccountStatement expected = as;
        assertEquals(response, expected);
    }
}
