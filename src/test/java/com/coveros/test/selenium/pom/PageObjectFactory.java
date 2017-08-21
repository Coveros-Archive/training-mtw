 package com.coveros.test.selenium.pom;

import org.openqa.selenium.WebDriver;

/**
 * Factory for creating page objects
 * 
 * @author brian
 *
 */
public final class PageObjectFactory {

	public static PageObjectFactory newInstance(WebDriver driver, String baseUrl) {
		return new PageObjectFactory(driver, baseUrl);
	}

	/**
	 * Set the global time delay to wait for a page to load. The default is 2s.
	 * Setting this value will cause the test execution thread to pause while
	 * creating a page object, giving the corresponding web page a chance to
	 * load.
	 * 
	 * @param delayMillis
	 *            the time to wait, in milliseconds
	 */
	public static void setDelay(int delayMillis) {
		_delayMillis = delayMillis;
	}

	private static int _delayMillis = 2000;

	private WebDriver driver;

	private PageObjectFactory(WebDriver driver, String baseUrl) {
		this.driver = driver;
		driver.get(baseUrl);
	}

	int getDelayMillis() {
		return _delayMillis;
	}

	/**
	 * Create a new instance of the specified page.
	 * 
	 * @param clazz
	 * @return
	 */
	public <T extends PageObject> T newPage(Class<T> clazz) {
		try {
			T t = clazz.newInstance();
			t.init(driver, this);
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
