package hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists;

public abstract class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
