package Exceptions;

public class NotFoundException extends Exception{

    public NotFoundException() {
        super("Pet not found, or pet had passed");
    }

}
