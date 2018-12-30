package com.diop.learning.retry;

import java.util.Arrays;

import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.SleepingBackOffPolicy;
import org.springframework.retry.backoff.ThreadWaitSleeper;

@SuppressWarnings("serial")
public class CustomBackOffPolicy implements SleepingBackOffPolicy<CustomBackOffPolicy> {

	private Sleeper sleeper = new ThreadWaitSleeper();

	private long[] intervals;

	public CustomBackOffPolicy(long[] intervals) {
		this.intervals = Arrays.copyOf(intervals, intervals.length);
	}

	public CustomBackOffPolicy() {
	}

	public Sleeper getSleeper() {
		return sleeper;
	}

	public void setSleeper(Sleeper sleeper) {
		this.sleeper = sleeper;
	}

	public long[] getIntervals() {
		return Arrays.copyOf(this.intervals, this.intervals.length);
	}

	public void setIntervals(long[] intervals) {
		this.intervals = Arrays.copyOf(intervals, intervals.length);
	}

	@Override
	public BackOffContext start(RetryContext context) {
		long[] interval = getIntervals();
		return new CustomBackOffContext(interval, 0);
	}

	@Override
	public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
		CustomBackOffContext context = (CustomBackOffContext) backOffContext;
		try {
			sleeper.sleep(context.getSleepAndIncrement());
		} catch (InterruptedException e) {
			throw new BackOffInterruptedException("Thread interrupted while sleeping", e);
		}
	}

	@Override
	public CustomBackOffPolicy withSleeper(Sleeper sleeper) {
		CustomBackOffPolicy res = new CustomBackOffPolicy();
		res.setSleeper(getSleeper());
		res.setIntervals(getIntervals());
		return res;
	}

	static class CustomBackOffContext implements BackOffContext {

		private int index;
		private long[] intervals;

		public CustomBackOffContext(long[] intervals, int index) {
			this.intervals = Arrays.copyOf(intervals, intervals.length);
			this.index = index;
		}

		public long getSleepAndIncrement() {
			synchronized(this){
				long sleep = intervals[index];
				if (index < intervals.length - 1) {
					index++;
				}
				return sleep;
			}
		}

		public long[] getIntervals() {
			return Arrays.copyOf(this.intervals, this.intervals.length);
		}

		public void setIntervals(long[] intervals) {
			this.intervals = Arrays.copyOf(intervals, intervals.length);
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

}