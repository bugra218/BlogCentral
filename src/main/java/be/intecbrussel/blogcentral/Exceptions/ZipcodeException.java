package be.intecbrussel.blogcentral.Exceptions;

public class ZipcodeException extends Exception {
    public ZipcodeException() {
        this("Zipcode entered incorrectly.");
    }

    public ZipcodeException(String message) {
        super(message);
    }
}
