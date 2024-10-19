package wcci.org.pawsclaws.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import Exceptions.NotFoundException;

// import wcci.org.virtualpet.Exceptions.ValidateException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Logger for recording error messages and details of the exceptions.
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // /**
    //  * Handles NotFoundException when a requested resource cannot be found.
    //  * Logs the error and returns a response with the error message and a "Gone" (410) HTTP status.
    //  * 
    //  * @param ex the NotFoundException that was thrown
    //  * @return ResponseEntity containing the error message and HTTP status code (410 - Gone)
    //  */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        // Log the error with the exception message and stack trace
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        // Return a response with the exception message and a 410 status code
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE); 
    }

    // /**
    //  * Handles ValidateException when validation errors occur.
    //  * Logs the error and returns a response with the error message and a "Forbidden" (403) HTTP status.
    //  * 
    //  * @param ex the ValidateException that was thrown
    //  * @return ResponseEntity containing the error message and HTTP status code (403 - Forbidden)
    //  */
    // @ExceptionHandler(ValidateException.class)
    // @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    // public ResponseEntity<String> handleValidateException(ValidateException ex) {
    //     // Log the validation error and the exception stack trace
    //     logger.error("An error occurred: {}", ex.getMessage(), ex);
    //     // Return a response with the exception message and a 403 status code
    //     return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    // }

    /**
     * Handles generic exceptions (any exceptions not explicitly handled by other methods).
     * Logs the error and returns a generic error message with an "Internal Server Error" (500) HTTP status.
     * 
     * @param ex the Exception that was thrown
     * @return ResponseEntity containing a generic error message and HTTP status code (500 - Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // Log the error details and stack trace for debugging purposes
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        // Return a response with a generic error message and a 500 status code
        return new ResponseEntity<>("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles IllegalArgumentException when invalid arguments are passed to a method.
     * Logs the warning and returns a response with the error message and a "Bad Request" (400) HTTP status.
     * 
     * @param ex the IllegalArgumentException that was thrown
     * @return ResponseEntity containing the error message and HTTP status code (400 - Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Log a warning message with the details of the illegal argument
        logger.warn("Illegal Argument: {}", ex.getMessage());
        // Return a response with the exception message and a 400 status code
        return new ResponseEntity<>("Invalid Input: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
