//Step 2: Create GlobalExceptionHandler class in exception package.
package com.blopapi.exception;

import com.blopapi.payload.ErrorDetails;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice //If exception accuses in the controller layer, that exception comes to in this class.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // handle specific exceptions
    // @ExceptionHandler(ResourceNotFoundException.class)
    @ExceptionHandler(Exception.class)//
    public ResponseEntity<ErrorDetails> handleGlobalExceptionException(Exception exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}


