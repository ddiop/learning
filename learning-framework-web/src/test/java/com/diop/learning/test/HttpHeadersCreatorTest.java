package com.diop.learning.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.HttpHeaders;

import com.diop.learning.web.rest.support.HttpHeadersCreator;



public class HttpHeadersCreatorTest {

	@Test
	public void shouldCreateCreationAlert() {
		HttpHeaders headers = HttpHeadersCreator.createEntityCreationAlert("enumLabel", "id1");
		assertThat(headers).isNotNull();
		assertThat(headers.get("X-learning-alert")).hasSize(1);
		assertThat(headers.get("X-learning-alert")).contains("learning.enumLabel.created");
		assertThat(headers.get("X-learning-params")).hasSize(1);
		assertThat(headers.get("X-learning-params")).contains("id1");
	}

	@Test
	public void shouldCreateCreationAlertWithoutParams() {
		HttpHeaders headers = HttpHeadersCreator.createEntityCreationAlert("enumLabel", null);
		assertThat(headers).isNotNull();
		assertThat(headers.get("X-learning-alert")).hasSize(1);
		assertThat(headers.get("X-learning-alert")).contains("learning.enumLabel.created");
		assertThat(headers.get("X-learning-params")).hasSize(1);
		assertThat(headers.get("X-learning-params")).contains((String) null);
	}
}
