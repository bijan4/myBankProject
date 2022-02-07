package com.mybank2.mybank.controllers;

import com.mybank2.mybank.dto.AccountStatement;
import com.mybank2.mybank.dto.TransactionStatementRequest;
import com.mybank2.mybank.services.IbankService;
import com.mybank2.mybank.utils.AccountNotFoundException;
import com.mybank2.mybank.utils.InsufficientFundsException;
import com.mybank2.mybank.utils.InvalidAmountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class MybankController {

    private static final String WITHDRAWAL_SUCCESS = "Retrait effectué avec succès";
    private static final String DEPOSIT_SUCCESS = "dépôt effectué avec succès";
    private final IbankService mybankService;

    @Autowired
    public MybankController(IbankService ibankService) {
        this.mybankService = ibankService;
    }

    /**
     * 
     * @param deposit
     * @return message
     * @throws InvalidAmountException
     * @throws AccountNotFoundException
     */
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionStatementRequest deposit)
            throws InvalidAmountException, AccountNotFoundException {

        mybankService.deposit(deposit);
        return new ResponseEntity<>(DEPOSIT_SUCCESS, HttpStatus.OK);

    }

    /**
     * 
     * @param withdrawal TransactionStatementRequest
     * @return message
     * @throws InvalidAmountException
     * @throws InsufficientFundsException
     * @throws AccountNotFoundException
     */
    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdrawal(@RequestBody TransactionStatementRequest withdrawal)
            throws InvalidAmountException,
            InsufficientFundsException, AccountNotFoundException {

        mybankService.withdrawal(withdrawal);
        return new ResponseEntity<>(WITHDRAWAL_SUCCESS, HttpStatus.OK);
    }

    /**
     * @param codeAcc
     * @return AccountStatement(currentBalance,transactionHistory[])
     */
    @GetMapping("/{codeAcc}/statement")
    public AccountStatement getStatement(
            @PathVariable("codeAcc") String codeAcc

    ) {
        return mybankService.getStatement(codeAcc);
    }
}
