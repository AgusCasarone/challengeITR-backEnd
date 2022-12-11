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

    private static final String messageResourceNotFoundException = "The resource you are trying to access " +
            "could not be found.";
    private static final String messageMissingValuesException = "The request body is missing values.";
    private static final String messageRosterAlreadyExistsException = "Only one roster can exist, so it is " +
            "not possible to add a new one.";
    private static final String messageNoLongerFINALException = "The roster would no longer have the conditions " +
            "to be FINAL so the change will not be possible until the roster.state is set as ENPROCESO.";
    private static final String messageFinalRosterCanNotBeChangedException = "Final roster can not be changed." +
            " Set the Roster.State to ENPROCESO and try again.";
    private static final String messageRosterCanNotBeFINALException = "Roster can not be final.";

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> processResourceNotFoundException(ResourceNotFoundException e) {
        LOGGER.error(messageResourceNotFoundException);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({MissingValuesException.class})
    public ResponseEntity<String> processExcepcionMissingValues(MissingValuesException e) {
        LOGGER.error(messageMissingValuesException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({RosterAlreadyExistsException.class})
    public ResponseEntity<String> processListaDeJugadoresAlreadyExistsException(RosterAlreadyExistsException e) {
        LOGGER.error(messageRosterAlreadyExistsException);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler({NoLongerFINALException.class})
    public ResponseEntity<String> processNoLongerFINALException(NoLongerFINALException e) {
        LOGGER.error(messageNoLongerFINALException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({FinalRosterCanNotBeChangedException.class})
    public ResponseEntity<String> processFinalRosterCanNotBeChangedException(FinalRosterCanNotBeChangedException e) {
        LOGGER.error(messageFinalRosterCanNotBeChangedException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({RosterCanNotBeFINALException.class})
    public ResponseEntity<String> processRosterCanNotBeFINALException(RosterCanNotBeFINALException e) {
        LOGGER.error(messageRosterCanNotBeFINALException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
