package exception;

import java.io.IOException;

public class LengthException extends IOException {
    public LengthException(String message, String length) {
        super(message + " не может быть " + length + "\n Введите заново: ");
    }
}
