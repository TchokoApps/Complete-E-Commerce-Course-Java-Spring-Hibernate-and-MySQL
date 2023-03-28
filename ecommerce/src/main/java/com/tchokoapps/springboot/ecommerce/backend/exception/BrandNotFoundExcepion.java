package com.tchokoapps.springboot.ecommerce.backend.exception;

public class BrandNotFoundExcepion extends Exception {

    public BrandNotFoundExcepion() {
        super();
    }

    public BrandNotFoundExcepion(String message) {
        super(message);
    }

    public BrandNotFoundExcepion(String message, Throwable cause) {
        super(message, cause);
    }

    public BrandNotFoundExcepion(Throwable cause) {
        super(cause);
    }

    protected BrandNotFoundExcepion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
