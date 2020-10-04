package hr.barisic.ivan.fejkbuk.exception.nonexistentresource;

public class NonexistentFriendshipRequestException extends NonexistentResourceException {

    private static final String MESSAGE = "Friendship request doesn't exist!";

    public NonexistentFriendshipRequestException() {
        super(MESSAGE);
    }

    public NonexistentFriendshipRequestException(String message) {
        super(message);
    }
}
