package com.mybank2.mybank.utils;

public class AccountNotFoundException extends RuntimeException {
   public AccountNotFoundException(){
    super();
 }

 public AccountNotFoundException(String account){
    super("Compte introuvable : " + account);
 }
}
