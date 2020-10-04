package hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists;

public class FriendshipAlreadyExistsException extends ResourceAlreadyExistsException {

    private static final String MESSAGE = "Friendship already exists!";

    public FriendshipAlreadyExistsException() {
        super(MESSAGE);
    }

    public FriendshipAlreadyExistsException(String message) {
        super(message);
    }
}
