package server.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;

public class ExchangeUtils {

	private HttpExchange exchange;

	public ExchangeUtils(HttpExchange exchange) {
		this.exchange = exchange;
	}

	public String getCommandName() {
		String fullURI = this.exchange.getRequestURI().getPath();
		int startIndex = fullURI.indexOf('/', fullURI.indexOf('/') + 1) + 1;
		String commandName = fullURI.substring(startIndex);
		return commandName;
	}

	public RequestType getRequestType() {
		return RequestType.valueOf(this.exchange.getRequestMethod());
	}

	public String getQuery() {
		return this.exchange.getRequestURI().getQuery();
	}

	public String getRequestBody() throws IOException {
		String body = "";
		try (Scanner s = new Scanner(this.exchange.getRequestBody())) {
			s.useDelimiter("\\A");
			body += s.next();
		} catch (Exception e) {

		}
		return body;
	}

	public void setContentType(String contentType) {
		this.exchange.getResponseHeaders().add("Content-Type", contentType);
	}

	public void setCookie(String cookieText) {
		this.exchange.getResponseHeaders().add("Set-cookie", cookieText);
	}

	public void sendResponseHeaders(int status, int length) throws IOException {
		this.exchange.sendResponseHeaders(status, length);
	}

	public void writeResponseBody(String responseBody)
			throws IOException {
		OutputStream os = this.exchange.getResponseBody();
		os.write(responseBody.getBytes());
		os.close();
	}

	public void close() {
		this.exchange.close();
	}

}
