package hr.barisic.ivan.fejkbuk.exception.nonexistentresource;

public class NonexistentPostException extends NonexistentResourceException {

    private static final String MESSAGE = "The post doesn't exist!";

    public NonexistentPostException() {
        super(MESSAGE);
    }

    public NonexistentPostException(String message) {
        super(message);
    }

    public NonexistentPostException(Long postId) {
        super("The post with id='" + postId + "' doesn't exist!");
    }
}
