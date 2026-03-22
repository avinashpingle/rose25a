package com.skillio.exceptions;

/**
 * This exception will be thrown when user passed invalid browser name
 * Valid browser names supported by this framework are:
 * 	<ul>
 * 		<li>Chrome</li>
 * 		<li>Firefox</li>
 * 		<li>Safari</li>
 * </ul>
 * 
 */
public class InvalidBrowserNameException extends RuntimeException{
	private String browserName;
	
	public InvalidBrowserNameException(String browserName) {
		this.browserName = browserName;
	}
	
	@Override
		public String getMessage() {
			return  this.browserName+" browser is not supported";
		}
}
