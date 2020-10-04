package hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists;

public class FriendshipRequestAlreadySentException extends ResourceAlreadyExistsException {

    private static final String MESSAGE = "Friendship request already sent!";

    public FriendshipRequestAlreadySentException() {
        super(MESSAGE);
    }

    public FriendshipRequestAlreadySentException(String message) {
        super(message);
    }
}
