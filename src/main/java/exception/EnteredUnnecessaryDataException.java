package exception;

import java.io.IOException;

public class EnteredUnnecessaryDataException extends IOException {
    public EnteredUnnecessaryDataException(String message) {
        super("Введены лишние данные или пробелы: " + message);
    }
}
