package Exceptions;

public class PlateIsEmpty extends RuntimeException {
    public PlateIsEmpty(String message) {
        super(message);
    }
}
