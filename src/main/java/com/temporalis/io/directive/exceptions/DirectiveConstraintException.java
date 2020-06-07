package com.temporalis.io.directive.exceptions;

public class DirectiveConstraintException extends RuntimeException {
  public DirectiveConstraintException(String message){
    super(message);
  }
  public DirectiveConstraintException(String message, Throwable t){
    super(message, t);
  }
}
