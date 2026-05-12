/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.exception;

public class ActionException
extends Exception {
    private static final long serialVersionUID = 1L;
    private Throwable thwStack;
    private String label = "unable_to_process_request";

    public ActionException(Exception excp) {
        super(excp);
        this.setThwStack(excp);
    }

    public ActionException(String msg, Throwable e) {
        super(msg, e);
        this.setThwStack(e);
    }

    public ActionException() {
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, String label, Throwable e) {
        super(message, e);
        this.setLabel(label);
    }

    public ActionException(String message, String label) {
        super(message);
        this.setLabel(label);
    }

    public Throwable getThwStack() {
        return this.thwStack;
    }

    public void setThwStack(Throwable throwable) {
        this.thwStack = throwable;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

