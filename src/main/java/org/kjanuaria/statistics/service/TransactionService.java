package org.kjanuaria.statistics.service;

import org.kjanuaria.statistics.entity.Transaction;
import org.kjanuaria.statistics.exception.TransactionExpiredException;
import org.kjanuaria.statistics.json.TransactionJson;

public interface TransactionService {

	public Transaction add(TransactionJson json) throws TransactionExpiredException;

}
