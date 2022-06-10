package be.intecbrussel.blogcentral.Exceptions;

public class UsernameNotAvailableException extends Exception {
    public UsernameNotAvailableException() {
        this("This username is already used. Please try another one.");
    }

    public UsernameNotAvailableException(String message) {
        super(message);
    }
}
