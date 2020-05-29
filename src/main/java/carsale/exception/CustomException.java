package carsale.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class CustomException extends RuntimeException {

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public String getFullStackTrace() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        this.printStackTrace(ps);
        return baos.toString();
    }
}
