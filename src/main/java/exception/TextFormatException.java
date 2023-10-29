package exception;

public class TextFormatException extends RuntimeException {
    public TextFormatException(String message) {
        super(message + " не может содержать дополнительные символы и цифры.\n" +
                "Введите " + message + ": ");
    }

}
