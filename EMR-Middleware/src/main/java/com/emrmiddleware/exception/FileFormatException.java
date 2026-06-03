
package com.emrmiddleware.exception;

public class FileFormatException
extends Exception {
    private static final long serialVersionUID = 1L;
    private Throwable thwStack;

    public FileFormatException(String s) {
        super(s);
    }

    public FileFormatException(String msg, Throwable e) {
        super(msg, e);
        this.setThwStack(e);
    }

    public FileFormatException(Exception excp) {
        super(excp);
        this.setThwStack(excp);
    }

    public FileFormatException() {
    }

    public Throwable getThwStack() {
        return this.thwStack;
    }

    public void setThwStack(Throwable throwable) {
        this.thwStack = throwable;
    }
}

