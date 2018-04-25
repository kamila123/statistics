package org.kjanuaria.statistics.entity;

import java.time.LocalDateTime;

public class Transaction {

	private LocalDateTime date;
	private Double amount;

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
