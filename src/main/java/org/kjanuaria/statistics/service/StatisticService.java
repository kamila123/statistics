package org.kjanuaria.statistics.service;

import org.kjanuaria.statistics.entity.Statistic;
import org.kjanuaria.statistics.entity.Transaction;
import org.kjanuaria.statistics.exception.TransactionExpiredException;

public interface StatisticService {

	public Statistic findCurrent();

	public void add(Transaction transaction) throws TransactionExpiredException;

}
