/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.yabx.kyc.app.interceptor;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

/**
 *
 * @author gauta
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor{
    private final String headerName;
	private final String headerValue;

	public HeaderRequestInterceptor(String headerName, String headerValue) {
		this.headerName = headerName;
		this.headerValue = headerValue;
	}

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequest wrapper = new HttpRequestWrapper(request);
		wrapper.getHeaders().set(headerName, headerValue);
		return execution.execute(wrapper, body);
    }
}
