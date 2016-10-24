/**
 * 
 */
package com.xaltome.smartcity.resourceconnector.http;

/**
 * @author kopajczy
 *
 */
public class HttpRequestFailureException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8073375311833396918L;
	private int statusCode = -1;
	
	public HttpRequestFailureException(final int statusCode){
		super();
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
