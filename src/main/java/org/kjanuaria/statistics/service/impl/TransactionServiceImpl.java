package org.kjanuaria.statistics.service.impl;

import org.kjanuaria.statistics.entity.Transaction;
import org.kjanuaria.statistics.exception.TransactionExpiredException;
import org.kjanuaria.statistics.json.TransactionJson;
import org.kjanuaria.statistics.service.StatisticService;
import org.kjanuaria.statistics.service.TransactionService;
import org.kjanuaria.statistics.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private StatisticService statisticService;

	@Override
	public Transaction add(TransactionJson json) throws TransactionExpiredException {

		Transaction transaction = new Transaction();
		transaction.setAmount(json.getAmount());
		transaction.setDate(DateUtil.convertToLocalDateTime(json.getTimestamp()));

		this.statisticService.add(transaction);

		return transaction;

	}

}
