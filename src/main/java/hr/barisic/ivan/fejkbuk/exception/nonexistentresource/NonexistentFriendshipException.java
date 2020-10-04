package hr.barisic.ivan.fejkbuk.exception.nonexistentresource;

public class NonexistentFriendshipException extends NonexistentResourceException {

    private static final String MESSAGE = "Friendship doesn't exist!";

    public NonexistentFriendshipException() {
        super(MESSAGE);
    }

    public NonexistentFriendshipException(String message) {
        super(message);
    }
}
