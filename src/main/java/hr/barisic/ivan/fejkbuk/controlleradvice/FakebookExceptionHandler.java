package hr.barisic.ivan.fejkbuk.controlleradvice;

import hr.barisic.ivan.fejkbuk.errorresponse.ErrorResponse;
import hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists.FriendshipAlreadyExistsException;
import hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists.FriendshipRequestAlreadySentException;
import hr.barisic.ivan.fejkbuk.exception.FriendshipRequestToSelfException;
import hr.barisic.ivan.fejkbuk.exception.nonexistentresource.NonexistentResourceException;
import hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists.ResourceAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FakebookExceptionHandler {

    //TODO don't know what to do in default case (with all other exceptions). Default JSON response only contains http status code 500 and message 'Internal server error', it doesn't contain exception message. Which is weird because most of the examples found online contain the exception message too in the default response. I found only one example online that doesn't contain exception message. I don't know if I should create an exception handler method for all the other exceptions beacuse in that case I'm not sure what http status code to use
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleFriendshipRequestToSelfException(FriendshipRequestToSelfException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNonexistentResourceException(NonexistentResourceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
