package com.coveros.training.mtw.selenium.pom;

/**
 * Exception indicating that a page did not load properly.
 * 
 * @author brian
 *
 */
public final class PageLoadException extends Exception {

	private static final long serialVersionUID = 1L;

	public PageLoadException(String message) {
		super(message);
	}
}
