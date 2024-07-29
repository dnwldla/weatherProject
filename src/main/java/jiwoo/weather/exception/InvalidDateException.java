package jiwoo.weather.exception;

public class InvalidDateException extends RuntimeException {
    private static final String message="날짜가 올바르지 않습니다";

    public InvalidDateException() {
        super(message);
    }
}
