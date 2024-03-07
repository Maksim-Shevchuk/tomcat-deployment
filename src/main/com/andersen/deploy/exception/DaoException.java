package com.andersen.deploy.exception;

import java.sql.SQLException;

public class DaoException extends SQLException {
    public DaoException(Throwable cause) {
        super(cause);
    }
}
