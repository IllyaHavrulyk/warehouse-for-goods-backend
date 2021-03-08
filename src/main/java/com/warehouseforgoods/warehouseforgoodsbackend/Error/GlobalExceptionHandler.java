package com.warehouseforgoods.warehouseforgoodsbackend.Error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductExceptions.class)
    public void springHandleProductExceptions(HttpServletResponse response,ProductExceptions ex) throws IOException {
       if(ex.getError().equals(ProductExceptions.Error.PRODUCT_DAO_GET_FAILED)){
           response.sendError(HttpStatus.NOT_FOUND.value());
       }
        if(ex.getError().equals(ProductExceptions.Error.PRODUCT_DAO_LIST_FAILED)){
            response.sendError(HttpStatus.NOT_FOUND.value());
        }
        if(ex.getError().equals(ProductExceptions.Error.PRODUCT_DAO_UPDATE_FAILED)){
            response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
        }
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public void userAlreadyExistExceptionHandler(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }
}
