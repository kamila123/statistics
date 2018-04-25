package org.kjanuaria.statistics.exception;

public class TransactionExpiredException extends Exception {

	private static final long serialVersionUID = -7240690911553888763L;

	public TransactionExpiredException() {
		super("Transaction is older than 60 seconds");
	}
}
