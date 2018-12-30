package com.diop.learning.retry;

import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public class CustomRetryTemplate extends RetryTemplate {

	public CustomRetryTemplate() {
		super();
	}

	public CustomRetryTemplate(Class<? extends Throwable> throwable, long[] delays) {
		super();

		// BackOffPolicy
		setBackOffPolicy(new CustomBackOffPolicy(delays));

		// RetryPolicy
		final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(delays.length + 1);
		ExceptionClassifierRetryPolicy retryPolicy = new ExceptionClassifierRetryPolicy();
		retryPolicy.setExceptionClassifier(classifiable -> choosePolicy(simpleRetryPolicy, classifiable, throwable));
		setRetryPolicy(retryPolicy);
	}

	public CustomRetryTemplate(long[] delays) {
		super();

		// BackOffPolicy
		setBackOffPolicy(new CustomBackOffPolicy(delays));

		// RetryPolicy
		final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(delays.length + 1);
		setRetryPolicy(simpleRetryPolicy);
	}

	public CustomRetryTemplate(Class<? extends Throwable> throwable, long[] delays, boolean throwLastExceptionOnExhausted) {
		this(throwable, delays);
		setThrowLastExceptionOnExhausted(throwLastExceptionOnExhausted);
	}
 
	private RetryPolicy choosePolicy(RetryPolicy retryPolicy, Throwable classifiable, Class<? extends Throwable> t) {
		if (t.isInstance(classifiable)) {
			return new NeverRetryPolicy();
		}
		return retryPolicy;
	}
}
