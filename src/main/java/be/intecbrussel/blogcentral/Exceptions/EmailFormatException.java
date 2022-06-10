package be.intecbrussel.blogcentral.Exceptions;

public class EmailFormatException extends Exception {
    public EmailFormatException() {
        this("Email entered incorrectly. Please review input and try again.");
    }

    public EmailFormatException(String message) {
        super(message);
    }
}
