package org.kjanuaria.statistics.exception;

public class TransactionInvalid extends Exception {

	private static final long serialVersionUID = -7240690911553888763L;

	public TransactionInvalid() {
		super("Invalid transaction timestamp");
	}

}
