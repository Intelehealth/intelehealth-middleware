package com.emrmiddleware.exception;

public class FileFormatException extends Exception {
	private static final long serialVersionUID = 1L;
	private Throwable thwStack;

	public FileFormatException(String s) {
		super(s);
	}

	public FileFormatException(String msg,Throwable e){
		super(msg,e);
		setThwStack(e);
		
	}
	public FileFormatException(Exception excp) {
		super(excp);
		setThwStack(excp);
	}

	public FileFormatException() {

		super();

	}

	public Throwable getThwStack() {
		return thwStack;
	}

	public void setThwStack(Throwable throwable) {
		thwStack = throwable;
	}

}
