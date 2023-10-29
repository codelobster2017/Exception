package exception;

import java.io.IOException;

public class YearBirthdateException extends IOException {
    public YearBirthdateException(String message) {
        super(message);
    }
}
