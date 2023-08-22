package kr.ac.jejunu.myproject.AuthUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(value = UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUserNameNotFoundException(){
        ErrorResponse response = new ErrorResponse(Code.BOARD_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
