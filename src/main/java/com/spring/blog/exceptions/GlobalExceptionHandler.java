package com.spring.blog.exceptions;

import com.spring.blog.payloads.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);

        return  new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errorResp = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String mess = error.getDefaultMessage();
            errorResp.put(fieldName, mess);
        });
        return new ResponseEntity<Map<String, String>>(errorResp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(final DataIntegrityViolationException ex){
        Map<String, String > errorResp = new HashMap<>();
        String message = ex.getMostSpecificCause().getMessage();

        Pattern pattern = Pattern.compile("Column '(.*?)' cannot be null");
        Matcher matcher = pattern.matcher(message);

        boolean found = false;
        while (matcher.find()) {
            found = true;
            String columnName = matcher.group(1);
            errorResp.put(columnName, columnName + " cannot be null.");
        }

        if (!found) {
            errorResp.put("error", "A data integrity violation occurred, but specific fields could not be determined.");
        }

        return new ResponseEntity<Map<String, String>>(errorResp, HttpStatus.BAD_REQUEST);
    }
}
