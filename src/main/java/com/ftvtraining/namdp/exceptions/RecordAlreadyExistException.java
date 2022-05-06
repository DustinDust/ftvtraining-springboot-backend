package com.ftvtraining.namdp.exceptions;

/*
* used when trying to insert a record but the record with the same id
* already exist
*/
public class RecordAlreadyExistException extends RuntimeException {
  private String message;

  public RecordAlreadyExistException(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
