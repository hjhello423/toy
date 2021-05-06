package com.github.hjdeepsleep.toy.exception;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String s) {
        super(s);
    }

    public NotEnoughStockException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotEnoughStockException(Throwable throwable) {
        super(throwable);
    }

}
