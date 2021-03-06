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

import java.net.URI;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Type utilities for Spring framework.
 *
 * @author Johnny Lim
 */
public final class SpringFrameworkHttpUtils {

	private SpringFrameworkHttpUtils() {
	}

	public static RequestEntity<Void> createGetRequestEntity(
			String url, HttpServletRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			Enumeration<String> headerValues = request.getHeaders(headerName);
			while (headerValues.hasMoreElements()) {
				String headerValue = headerValues.nextElement();
				headers.add(headerName, headerValue);
			}
		}

		String queryString = request.getQueryString();
		if (queryString != null) {
			url += "?" + queryString;
		}
		return new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
	}

}
