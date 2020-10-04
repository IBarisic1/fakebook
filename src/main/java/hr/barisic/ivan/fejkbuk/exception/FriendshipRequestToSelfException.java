package hr.barisic.ivan.fejkbuk.exception;

public class FriendshipRequestToSelfException extends RuntimeException {

    private static final String MESSAGE = "Create friendship with self not allowed!";

    public FriendshipRequestToSelfException() {
        super(MESSAGE);
    }

    public FriendshipRequestToSelfException(String message) {
        super(message);
    }
}
