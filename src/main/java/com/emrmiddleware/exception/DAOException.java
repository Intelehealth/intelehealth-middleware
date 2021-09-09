package com.emrmiddleware.exception;

public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;
	private Throwable thwStack;

	public DAOException(Exception excp) {
		super(excp);
		setThwStack(excp);
		
	}

	public DAOException(String msg, Throwable e)
	{
		super(msg,e);
		setThwStack(e);
		
	}
	public DAOException() {

		super();

	}

	public DAOException(String message) {
		super(message);
	}

	public Throwable getThwStack() {
		return thwStack;
	}

	public void setThwStack(Throwable throwable) {
		thwStack = throwable;
	}

}