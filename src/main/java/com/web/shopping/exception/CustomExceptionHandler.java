package com.web.shopping.exception;

import com.web.shopping.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleCustomErrorException(CustomException exception) {
        log.error("throw customException : {}", exception.getErrorCode());
        ResponseDto restApiException = new ResponseDto(exception.getErrorCode().getHttpStatus().value(), exception.getErrorCode().getDetail(), "", "");
        return new ResponseEntity<>(restApiException, exception.getErrorCode().getHttpStatus());
    }
}