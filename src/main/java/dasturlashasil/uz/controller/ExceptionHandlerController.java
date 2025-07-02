package dasturlashasil.uz.controller;

import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.exceptons.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AppBadException.class , NotFoundException.class} )
    public ResponseEntity handeBadRequest(RuntimeException e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

//    @ExceptionHandler({NotFoundException.class})
//    public ResponseEntity<String> handleNotFound(NotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler({RuntimeException.class} )
    public ResponseEntity handle(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = new LinkedList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }


}
