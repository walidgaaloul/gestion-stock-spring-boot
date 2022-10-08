package com.gaaloul.gestiondestock.exception;

import lombok.Getter;

public class InvalideOperationException extends RuntimeException {

        @Getter
        private ErrorCodes errorCode;

    public InvalideOperationException(String message) {
            super(message);

        }

    public InvalideOperationException(String message , Throwable cause) {
            super(message , cause);

        }

    public InvalideOperationException(String message,Throwable cause , ErrorCodes errorCode) {
            super(message , cause );
            this.errorCode = errorCode;

        }

    public InvalideOperationException(String message , ErrorCodes errorCode) {
            super(message);
            this.errorCode = errorCode;

        }

}
