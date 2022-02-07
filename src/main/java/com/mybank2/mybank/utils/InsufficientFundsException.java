package com.mybank2.mybank.utils;

public class InsufficientFundsException extends Exception{
      // no-arg constructor
   public InsufficientFundsException(){
    super();
 }

 public InsufficientFundsException(String msg){
    // String-arg constructor
    super(msg);
 }
}
