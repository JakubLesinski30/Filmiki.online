package Filmiki.online;

public class WyjatekPrzechowania extends RuntimeException {
    public WyjatekPrzechowania(String message) {
        super(message);
    }

    public WyjatekPrzechowania(String message, Throwable cause) {
        super(message, cause);
    }
}