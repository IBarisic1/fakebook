package hr.barisic.ivan.fejkbuk.exception.nonexistentresource;

public class NonexistentPersonException extends NonexistentResourceException {

    private static final String MESSAGE = "The person doesn't exist!";

    public NonexistentPersonException() {
        super(MESSAGE);
    }

    public NonexistentPersonException(String message) {
        super(message);
    }

    public NonexistentPersonException(long personId) {
        super("The person with id='" + personId + "' doesn't exist!");
    }
}
