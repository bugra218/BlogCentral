package be.intecbrussel.blogcentral.Exceptions;

public class RequiredFieldsException extends Exception {
    public RequiredFieldsException() {
        this("Please fill in all required fields.");
    }

    public RequiredFieldsException(String message) {
        super(message);
    }
}
