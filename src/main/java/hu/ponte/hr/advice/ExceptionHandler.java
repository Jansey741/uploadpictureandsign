package hu.ponte.hr.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
        public final ResponseEntity<ErrorMessage> handleAllExceptions(Exception ex, WebRequest request) {
            List<String> details = new ArrayList<>();
            details.add(ex.getLocalizedMessage());
            ErrorMessage error = new ErrorMessage("", details);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}

