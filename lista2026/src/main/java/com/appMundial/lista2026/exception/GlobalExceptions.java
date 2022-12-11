package com.appMundial.lista2026.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptions.class);

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> processExcepcionNotFound(ResourceNotFoundException e) {
        LOGGER.error("The resource you are trying to access could not be found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({MissingValuesException.class})
    public ResponseEntity<String> processExcepcionMissingValues(MissingValuesException e) {
        LOGGER.error("The request body is missing values.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({RosterAlreadyExistsException.class})
    public ResponseEntity<String> processListaDeJugadoresAlreadyExistsException(RosterAlreadyExistsException e) {
        LOGGER.error("Only one roster can exist, so it is not possible to add a new one.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler({NoLongerFINALException.class})
    public ResponseEntity<String> processNoLongerFINALException(NoLongerFINALException e) {
        LOGGER.error("The roster would no longer have the conditions to be FINAL so the change will not be possible until the roster.state is set as ENPROCESO.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
    }

}
