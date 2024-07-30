package com.example.exchangerate.exception;

public class ApiDataNotFoundException extends RuntimeException{
  public ApiDataNotFoundException(String message){
    super(message);
  }
}
