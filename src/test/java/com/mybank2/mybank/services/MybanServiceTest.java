package com.mybank2.mybank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mybank2.mybank.dao.AccountRepository;
import com.mybank2.mybank.dao.TransactionRepository;
import com.mybank2.mybank.dao.UserRepository;
import com.mybank2.mybank.dto.AccountStatement;
import com.mybank2.mybank.dto.TransactionStatementRequest;
import com.mybank2.mybank.entities.Account;
import com.mybank2.mybank.entities.Deposit;
import com.mybank2.mybank.entities.Transaction;
import com.mybank2.mybank.entities.User;
import com.mybank2.mybank.entities.Withdrawal;
import com.mybank2.mybank.utils.AccountNotFoundException;
import com.mybank2.mybank.utils.InsufficientFundsException;
import com.mybank2.mybank.utils.InvalidAmountException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MybanServiceTest {

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    AccountRepository accountRepositoryMock;

    @Mock
    TransactionRepository transactionRepositoryMock;

    @InjectMocks
    IbankServiceImpl service;

    @Test
    public void shouldWithdrawalAPositiveAmount() throws InvalidAmountException,
            InsufficientFundsException, AccountNotFoundException {
        User userFoo = new User("foo", "foo@gmail.com");
        Account acc1 = new Account(LocalDateTime.now(), "code1", 100, userFoo);

        when(accountRepositoryMock.findByAccountNumberEquals("code1")).thenReturn(acc1);

        TransactionStatementRequest tt = new TransactionStatementRequest();
        tt.setAmount(10);
        tt.setAccountNumber("code1");

        service.withdrawal(tt);
        assertEquals(acc1.getBalance(), 90);
        verify(accountRepositoryMock, times(1)).findByAccountNumberEquals("code1");
    }

    @Test
    public void shouldWithdrawalFailInsufficientFunds() throws InvalidAmountException,
            InsufficientFundsException, AccountNotFoundException {
        User userFoo = new User("foo", "foo@gmail.com");
        Account acc1 = new Account(LocalDateTime.now(), "code1", 100, userFoo);

        when(accountRepositoryMock.findByAccountNumberEquals("code1")).thenReturn(acc1);

        TransactionStatementRequest tt = new TransactionStatementRequest();
        tt.setAmount(1000);
        tt.setAccountNumber("code1");
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> service.withdrawal(tt));
        assertEquals("Solde insuffisant", exception.getMessage());
    }

    @Test
    public void shouldDepositAPositiveAmount() throws InvalidAmountException,
            AccountNotFoundException {
        User userFoo = new User("foo", "foo@gmail.com");
        Account acc1 = new Account(LocalDateTime.now(), "code1", 100, userFoo);

        when(accountRepositoryMock.findByAccountNumberEquals("code1")).thenReturn(acc1);

        TransactionStatementRequest tt = new TransactionStatementRequest();
        tt.setAmount(10);
        tt.setAccountNumber("code1");

        service.deposit(tt);
        assertEquals(acc1.getBalance(), 110);
        verify(accountRepositoryMock, times(1)).findByAccountNumberEquals("code1");
    }

    @Test
    public void shouldDepositFailNoAccountFound() throws InvalidAmountException,
            InsufficientFundsException, AccountNotFoundException {

        when(accountRepositoryMock.findByAccountNumberEquals("code1")).thenReturn(null);

        TransactionStatementRequest tt = new TransactionStatementRequest();
        tt.setAmount(1000);
        tt.setAccountNumber("code1");
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> service.deposit(tt));
        assertEquals("Compte introuvable : code1", exception.getMessage());
    }

    @Test
    public void shouldGetTransactionsByAccountNumber() {
        User user1 = new User("Najib", "najib@gmail.com");
        Account account = new Account(LocalDateTime.now(), "code1", 2000, user1);

        Transaction deposit = new Deposit(LocalDateTime.now(), 1000, "code1");
        Transaction whithdrawal = new Withdrawal(LocalDateTime.now(), 1000, "code1");

        List<Transaction> transactions = new ArrayList<Transaction>();

        transactions.add(deposit);
        transactions.add(whithdrawal);

        when(accountRepositoryMock.findByAccountNumberEquals("code1")).thenReturn(account);
        when(transactionRepositoryMock.findByAccountNumberEquals("code1")).thenReturn(transactions);

        AccountStatement ts = service.getStatement("code1");

        assertEquals(ts.getCurrentBalance(), 2000);
        assertEquals(ts.getTransactionHistory().size(), 2);
    }
}
