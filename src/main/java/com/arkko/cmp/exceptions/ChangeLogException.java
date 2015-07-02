package com.arkko.cmp.exceptions;

/**
 *
 * @author slapcevic
 */
public class ChangeLogException extends RuntimeException {

    public ChangeLogException(String logMessage) {
        super(logMessage);
    }

    public ChangeLogException(String logMessage, Throwable reason) {
        super(logMessage, reason);
    }
}
