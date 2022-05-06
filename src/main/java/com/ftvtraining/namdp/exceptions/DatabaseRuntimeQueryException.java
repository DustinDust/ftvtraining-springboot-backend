package com.ftvtraining.namdp.exceptions;

public class DatabaseRuntimeQueryException extends RuntimeException {
   private String message;
   private String errorCode;

   public DatabaseRuntimeQueryException(String message, String errorcode) {
      this.message = message;
      this.errorCode = errorcode;
   }

   @Override
   public String getMessage() {
      return this.message;
   }

   public String getErrorCode() {
      return errorCode;
   }
}
