/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.izeye.util.spring;

import java.util.Arrays;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringFrameworkHttpUtils}.
 *
 * @author Johnny Lim
 */
public class SpringFrameworkHttpUtilsTests {

	@Test
	public void testCreateGetRequestEntity() {
		String url = "http://internal.izeye.com/another-path";

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("header1", "value11");
		request.addHeader("header1", "value12");
		request.addHeader("header2", "value21");
		request.setQueryString("param1=value1&param2=value2");

		RequestEntity<Void> requestEntity = SpringFrameworkHttpUtils
				.createGetRequestEntity(url, request);

		assertThat(requestEntity.getHeaders())
				.hasSize(2)
				.containsEntry("header1", Arrays.asList("value11", "value12"))
				.containsEntry("header2", Arrays.asList("value21"));
		assertThat(requestEntity.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(requestEntity.getUrl().toString())
				.isEqualTo("http://internal.izeye.com/another-path?param1=value1&param2=value2");
	}

}
