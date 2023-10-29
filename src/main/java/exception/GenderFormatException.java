package exception;

import java.io.IOException;

public class GenderFormatException extends IOException {
    public GenderFormatException(String message) {
        super(message);
    }
}
