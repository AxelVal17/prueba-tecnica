package com.axel.commons.exceptions;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.axel.commons.dtos.ErrorResponseDTO;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(ConstraintViolationException.class)
	    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException e) {
	        log.error("Violación de restricción: {}", e.getMessage());
	        return ResponseEntity.badRequest()
	                .body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), "Violación de restricción: " + e.getMessage()));
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
	        String mensaje = e.getBindingResult().getFieldErrors().stream()
	                .map(err -> err.getField() + ": " + err.getDefaultMessage())
	                .findFirst()
	                .orElse("Error de validación en los datos enviados");
	        log.error("Error de validación de argumentos: {}", mensaje);
	        return ResponseEntity.badRequest()
	                .body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), mensaje));
	    }

	    @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<ErrorResponseDTO> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
	        log.error("Error al leer la petición HTTP: {}", e.getMessage());
	        return ResponseEntity.badRequest().body(
	                new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMostSpecificCause().getMessage())
	        );
	    }

	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<ErrorResponseDTO> IllegalArgumentException(IllegalArgumentException e) {
	        log.error("Error en la petición: {}", e.getMessage());
	        return ResponseEntity.badRequest().body(
	                new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage())
	        );
	    }


	    @ExceptionHandler(NoSuchElementException.class)
	    public ResponseEntity<ErrorResponseDTO> NoSuchElementException(NoSuchElementException e) {
	        log.warn("No se encontró el recurso: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
	                new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), e.getMessage())
	        );
	    }

	    @ExceptionHandler(NoResourceFoundException.class)
	    public ResponseEntity<ErrorResponseDTO> handleNoResourceFoundException(NoResourceFoundException e) {
	        log.warn("No se encontró el recurso estático: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
	                new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), e.getMessage())
	        );
	    }
}
