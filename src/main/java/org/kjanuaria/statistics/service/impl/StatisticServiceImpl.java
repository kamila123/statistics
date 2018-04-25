package org.kjanuaria.statistics.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import org.kjanuaria.statistics.entity.Statistic;
import org.kjanuaria.statistics.entity.Transaction;
import org.kjanuaria.statistics.exception.TransactionExpiredException;
import org.kjanuaria.statistics.exception.TransactionInvalid;
import org.kjanuaria.statistics.service.StatisticService;
import org.kjanuaria.statistics.util.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 
 * Statistics Service
 * 
 * @author Kjanuaria
 *
 */
@Service
public class StatisticServiceImpl implements StatisticService {

	// Lock for ADD / REMOVE
	private Object lock = new Object();

	// Memory Complexity: O(${windowInMs} / 1000 +
	// ${removeExpiredStatisticsInMs} / 1000) -> O(1)
	private Map<Long, Statistic> statisticHistory;

	// Memory Complexity: O(${windowInMs} / 1000 +
	// ${removeExpiredStatisticsInMs} / 1000) -> O(1)

	private Stack<Long> statisticTimestamps;

	private Statistic statistic;

	// @Value("60000")
	private Long windowInMs = (long) 6000;

	public StatisticServiceImpl() {
		this.statisticHistory = new ConcurrentHashMap<Long, Statistic>();
		this.statisticTimestamps = new Stack<Long>();
	}

	/**
	 * 
	 * Create init statistic
	 * 
	 * Time complexity O(1)
	 * 
	 * @return Statistic with init values
	 */
	private Statistic createFirstStatistic(Long timestamp) {
		Statistic statistic = new Statistic();
		statistic.setDate(DateUtil.convertToLocalDateTime(timestamp));
		statistic.setMax(Double.MIN_VALUE);
		statistic.setMin(Double.MAX_VALUE);
		statistic.setSum(0.0);
		statistic.setCount(0l);
		return statistic;
	}

	/**
	 * 
	 * Remove expired statistics
	 * 
	 * Time complexity: O(statisticTimestamps.size() * log
	 * statisticTimestamps.size()) -> O(1)
	 * 
	 */
	@Scheduled(fixedDelayString = "10000")
	private void removeExpiredStatistics() {

		Long currentTimestamp = DateUtil.converToTimeStamp(LocalDateTime.now());

		if (this.statisticTimestamps.isEmpty() || this.statisticTimestamps.peek() >= currentTimestamp)
			return;

		synchronized (lock) {

			// O(n) - Where n = statisticTimestamps.size()
			while (!this.statisticTimestamps.isEmpty() && this.statisticTimestamps.peek() < currentTimestamp) {

				// O(log n) - Where n = statisticTimestamps.size()
				Long key = this.statisticTimestamps.pop();

				// O(1)
				this.statisticHistory.remove(key);
			}
		}
	}

	/**
	 * 
	 * Add statistic
	 * 
	 * Time complexity: O(${windowInMs}/1000 * log statisticTimestamps.size())
	 * -> O(1)
	 * 
	 * @throws TransactionInvalid
	 * 
	 */
	@Override
	public void add(Transaction transaction) throws TransactionExpiredException {

		Long currentTimestamp = DateUtil.converToTimeStamp(LocalDateTime.now());
		Long transactionTimestamp = DateUtil.converToTimeStamp(transaction.getDate());

		if (transactionTimestamp + windowInMs < currentTimestamp) {
			throw new TransactionExpiredException();
		}

		synchronized (lock) {

			if (this.statistic == null) {

				// O(1)
				this.statistic = this.createFirstStatistic(transactionTimestamp);

				// O(1)
				this.statisticHistory.put(transactionTimestamp, statistic);

				// O(log n) - Where n = statisticTimestamps.size()
				this.statisticTimestamps.add(transactionTimestamp);
			}

			if (transaction.getAmount() > statistic.getMax())
				statistic.setMax(transaction.getAmount());
			if (transaction.getAmount() < statistic.getMin())
				statistic.setMin(transaction.getAmount());

			statistic.setSum(statistic.getSum() + transaction.getAmount());
			statistic.setCount(statistic.getCount() + 1);
			statistic.setAvg(statistic.getSum() / statistic.getCount());
		}

	}

	/**
	 * Return current statistic
	 */
	@Override
	public Statistic findCurrent() {

		return this.statistic;
	}

}