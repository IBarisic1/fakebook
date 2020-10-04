package hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists;

public class FriendshipRequestAlreadyReceivedException extends ResourceAlreadyExistsException {

    private static final String MESSAGE = "Friendship request already received!";

    public FriendshipRequestAlreadyReceivedException() {
        super(MESSAGE);
    }

    public FriendshipRequestAlreadyReceivedException(String message) {
        super(message);
    }
}
