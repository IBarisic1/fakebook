package hr.barisic.ivan.fejkbuk.exception.nonexistentresource;

public abstract class NonexistentResourceException extends RuntimeException {

    public NonexistentResourceException(String message) {
        super(message);
    }
}
