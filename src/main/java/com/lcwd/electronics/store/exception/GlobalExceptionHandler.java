package com.lcwd.electronics.store.exception;

import com.lcwd.electronics.store.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handler resource not found exception

    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
     @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        logger.info("Exception handler invoked");
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(true)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
    }

    //handle Method Argument Not Valid Exception ->>>exception handling of validation Api

    @ExceptionHandler(MethodArgumentNotValidException.class)
      public ResponseEntity<Map<String,Object>> handleMethodArgumentNotNotException(MethodArgumentNotValidException ex)
      {
          //extract all validation error

          List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
          //for store the key value
          Map<String,Object> response = new HashMap<>(); //create object of Map
          allErrors.stream().forEach(objectError->{  //objectError get message
              String message = objectError.getDefaultMessage(); //here
              String field = ((FieldError) objectError).getField(); //here get filed
              response.put(field,message);
          });
          return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

      }

      //handle Bad Api Request
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex)
    {
        logger.info("Bad Api Request!!");
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
    }


}
