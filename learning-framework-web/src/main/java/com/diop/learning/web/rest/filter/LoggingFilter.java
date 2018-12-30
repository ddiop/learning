package com.diop.learning.web.rest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.diop.learning.web.rest.jwt.JwtTools;

@Component
public class LoggingFilter implements Filter {

	@Value("${spring.cloud.client.hostname}")
	private String hostName;
	@Value("${spring.application.name}")
	private String appName;
	@Autowired
	private JwtTools jwtTools;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// empty
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			// Validate header data
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			//String connectedId = jwtTools.extractUserIdFromHeaderRequest(httpRequest);
			String connectedId = "admin";

			String clientIp = httpRequest.getHeader("X-FORWARDED-FOR");
			if (clientIp == null || "".equals(clientIp)) {
				clientIp = httpRequest.getRemoteAddr();
			}

			// INFOS LOGGER
			MDC.put("IP", clientIp);
			MDC.put("UserID", connectedId);
			MDC.put("HostName", hostName);
			MDC.put("AppName", appName);
			chain.doFilter(request, response);
		} finally {
			// Tear down MDC data:
			// ( Important! Cleans up the ThreadLocal data again )
			MDC.clear();
		}
	}

	@Override
	public void destroy() {
		// empty
	}

}
