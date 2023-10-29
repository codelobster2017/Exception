package exception;

import java.io.IOException;

public class NotAllDataEnteredException extends IOException {
    public NotAllDataEnteredException(String message, String dataFormat) {
        super("Не все данные введены, вы ввели: " + message + "\n Введите:" + dataFormat);
    }
}
