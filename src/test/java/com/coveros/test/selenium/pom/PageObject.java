package com.coveros.test.selenium.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Abstract superclass for all page objects. If page objects extend this class
 * then they can be instantiated via the {@link PageObjectFactory}.
 * 
 * <p>
 * It is recommended that page objects that extend this class use Selenium
 * PageFactory annotations to find web elements.
 * 
 * @author brian
 *
 */
public abstract class PageObject {

	protected WebDriver driver;
	protected PageObjectFactory factory;

	void init(WebDriver driver, PageObjectFactory factory) {
		this.driver = driver;
		this.factory = factory;
		try {
			Thread.sleep(factory.getDelayMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PageFactory.initElements(driver, this);
		if (!checkInitialPageState()) {
			throw new Error();
		}
		
	}

	protected boolean checkInitialPageState() {
		return true;
	}
}
