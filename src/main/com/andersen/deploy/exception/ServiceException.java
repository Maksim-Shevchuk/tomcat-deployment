package com.andersen.deploy.exception;

import java.sql.SQLException;

public class ServiceException extends SQLException {
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
