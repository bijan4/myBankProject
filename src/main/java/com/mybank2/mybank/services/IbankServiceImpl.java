package com.mybank2.mybank.services;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.mybank2.mybank.dao.AccountRepository;
import com.mybank2.mybank.dao.TransactionRepository;
import com.mybank2.mybank.dao.UserRepository;
import com.mybank2.mybank.dto.AccountStatement;
import com.mybank2.mybank.dto.TransactionStatementRequest;
import com.mybank2.mybank.entities.Account;
import com.mybank2.mybank.entities.Deposit;
import com.mybank2.mybank.entities.Withdrawal;
import com.mybank2.mybank.utils.AccountNotFoundException;
import com.mybank2.mybank.utils.InsufficientFundsException;
import com.mybank2.mybank.utils.InvalidAmountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class IbankServiceImpl implements IbankService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public IbankServiceImpl(AccountRepository accountRepository,
            UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;

    }

    /**
     * 
     * @param deposit TransactionStatementRequest
     * @return message
     * @throws InvalidAmountException
     * @throws AccountNotFoundException
     */
    @Override
    public Withdrawal withdrawal(TransactionStatementRequest transactionStatementRequest) throws InvalidAmountException,
            InsufficientFundsException, AccountNotFoundException {
        String accountNumber = transactionStatementRequest.getAccountNumber();
        double amount = transactionStatementRequest.getAmount();

        Account account = accountRepository.findByAccountNumberEquals(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }

        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        if (account.getBalance() >= amount) {
            Withdrawal w = new Withdrawal(LocalDateTime.now(), amount, accountNumber);
            account.setBalance(account.getBalance() - amount);
            transactionRepository.save(w);
            accountRepository.save(account);
            return w;
        } else {
            throw new InsufficientFundsException("Solde insuffisant");
        }
    }

    /**
     * @param withdrawal TransactionStatementRequest
     * @return message
     * @throws InvalidAmountException
     * @throws InsufficientFundsException
     * @throws AccountNotFoundException
     */
    @Override
    public Deposit deposit(TransactionStatementRequest transactionStatementRequest)
            throws InvalidAmountException, AccountNotFoundException {
        String accountNumber = transactionStatementRequest.getAccountNumber();
        double amount = transactionStatementRequest.getAmount();
        Account account = accountRepository.findByAccountNumberEquals(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }

        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        Deposit depo = new Deposit(LocalDateTime.now(), amount, accountNumber);
        account.setBalance(account.getBalance() + amount);
        transactionRepository.save(depo);
        accountRepository.save(account);
        return depo;
    }

    /**
     * @param codeAcc
     * @return AccountStatement(currentBalance,transactionHistory[])
     */
    @Override
    public AccountStatement getStatement(String accountNumber) {
        Account account = accountRepository.findByAccountNumberEquals(accountNumber);
        return new AccountStatement(account.getBalance(),
                transactionRepository.findByAccountNumberEquals(accountNumber));
    }
}
