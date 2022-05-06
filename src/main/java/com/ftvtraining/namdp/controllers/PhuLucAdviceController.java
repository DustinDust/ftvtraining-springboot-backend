package com.ftvtraining.namdp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.ftvtraining.namdp.dto.ErrorResponse;
import com.ftvtraining.namdp.dto.Violation;
import com.ftvtraining.namdp.exceptions.DatabaseRuntimeQueryException;
import com.ftvtraining.namdp.exceptions.RecordAlreadyExistException;
import com.ftvtraining.namdp.exceptions.RefreshTokenException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class PhuLucAdviceController {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleAllException(Exception exception, WebRequest wr) {
    exception.printStackTrace();
    List<Violation> vlist = new ArrayList<>();
    vlist.add(new Violation(exception.getClass().toString(), exception.getMessage()));
    return new ErrorResponse(false, vlist);
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFoundException(NoSuchElementException e, WebRequest wr) {
    List<Violation> vlist = new ArrayList<>();
    vlist.add(new Violation("NoSuchElementException", e.getMessage()));
    return new ErrorResponse(false, vlist);
  }

  @ExceptionHandler(RecordAlreadyExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleAlreadyExistException(RecordAlreadyExistException e, WebRequest wr) {
    List<Violation> vlist = new ArrayList<>();
    vlist.add(new Violation("RecordAlreadyExist", e.getMessage()));
    return new ErrorResponse(false, vlist);
  }

  @ExceptionHandler(DatabaseRuntimeQueryException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleQueryException(DatabaseRuntimeQueryException e, WebRequest wr) {
    List<Violation> vlist = new ArrayList<>();
    vlist.add(new Violation(e.getErrorCode(), e.getMessage()));
    return new ErrorResponse(false, vlist);

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidationException(MethodArgumentNotValidException e, WebRequest wr) {
    List<Violation> vList = new ArrayList<>();
    for (FieldError error : e.getFieldErrors()) {
      vList.add(new Violation(error.getField(), error.getDefaultMessage()));
    }
    return new ErrorResponse(false, vList);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraintViolationException(ConstraintViolationException e, WebRequest wr) {
    List<Violation> vList = new ArrayList<>();
    for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
      vList.add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
    }
    return new ErrorResponse(false, vList);
  }

  @ExceptionHandler(RefreshTokenException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handleRefreshTokenException(RefreshTokenException ex, WebRequest wr) {
	  List<Violation> vlist = new ArrayList<>();
	  vlist.add(new Violation(ex.getType(), ex.getMessage()));
	  return new ErrorResponse(false, vlist);
  }
}