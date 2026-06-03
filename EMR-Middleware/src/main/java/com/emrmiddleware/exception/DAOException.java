
package com.emrmiddleware.exception;

public class DAOException
extends Exception {
    private static final long serialVersionUID = 1L;
    private Throwable thwStack;

    public DAOException(Exception excp) {
        super(excp);
        this.setThwStack(excp);
    }

    public DAOException(String msg, Throwable e) {
        super(msg, e);
        this.setThwStack(e);
    }

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public Throwable getThwStack() {
        return this.thwStack;
    }

    public void setThwStack(Throwable throwable) {
        this.thwStack = throwable;
    }
}

