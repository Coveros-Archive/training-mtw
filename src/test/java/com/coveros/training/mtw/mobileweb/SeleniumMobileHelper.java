package com.coveros.training.mtw.mobileweb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Example of how to extract Selenium into helper class.
 * 
 * @author brian
 *
 */
public final class SeleniumMobileHelper {

	public enum Locator {
		ID, XPATH, CSS, LINK, PARTIAL_LINK
	}

	private WebDriver driver;

	private int TIMEOUT = 10;

	private String baseUrl;

	public SeleniumMobileHelper(WebDriver driver, String baseUrl) {
		this.driver = driver;
		this.baseUrl = baseUrl;
		driver.get(baseUrl);
	}

	public final void setTimeout(int seconds) {
		this.TIMEOUT = seconds;
	}

	private By getSeleniumLocator(Locator loc, String text) {
		switch (loc) {
		case ID:
			return By.id(text);
		case XPATH:
			return By.xpath(text);
		case CSS:
			return By.cssSelector(text);
		case PARTIAL_LINK:
			return By.partialLinkText(text);
		case LINK:
			return By.linkText(text);
		default:
			return null;

		}
	}

	/**
	 * Explicitly wait for the text to appear in the element indicated by the
	 * locator.
	 * 
	 * @param locator
	 *            the locator type for which to wait
	 * @param the
	 *            text describing the element to locate
	 * @param value
	 *            the text that should appear in the element
	 * @return return <code>true</code> if the text appears in the specified
	 *         element prior to the default timeout. Return <code>false</code>
	 *         if the text is not found or the timeout period expires
	 */
	public boolean isTextInElementWithExplicitWait(Locator locator, String locatorText, String value) {
		throwIfNull(locator, "locator");
		By by = getSeleniumLocator(locator, locatorText);
		return new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.textToBePresentInElementLocated(by, value));
	}

	/**
	 * Find and click the element indicated by the locator.
	 * 
	 * @param locator
	 *            the locator describing the element to be clicked
	 */
	private final void tapElement(By locator) {
		throwIfNull(locator, "locator");
		driver.findElement(locator).click();
	}

	public final void tapElement(Locator locator, String locatorText) {
		tapElement(getSeleniumLocator(locator, locatorText));
	}

	private final void typeTextInto(By locator, String text) {
		throwIfNull(locator, "locator");
		driver.findElement(locator).sendKeys(text);
	}

	public final void typeTextInto(Locator locator, String locatorText, String text) {
		typeTextInto(getSeleniumLocator(locator, locatorText), text);
	}

	private final void clearField(By locator) {
		throwIfNull(locator, "locator");
		driver.findElement(locator).clear();
	}

	public final void clearField(Locator locator, String locatorText) {
		clearField(getSeleniumLocator(locator, locatorText));
	}

	/**
	 * Select a value from an element that presents multiple choices (e.g. a
	 * drop-down or other selector)
	 * 
	 * @param locator
	 * @param choice
	 */
	private void selectFromVisibleChoices(By locator, String choice) {
		throwIfNull(locator, "locator");
		new Select(driver.findElement(locator)).selectByVisibleText(choice);
	}

	public final void selectFromVisibleChoices(Locator locator, String locatorText, String choice) {
		selectFromVisibleChoices(getSeleniumLocator(locator, locatorText), choice);
	}

	public String getElementText(Locator loc, String locatorText) {
		return driver.findElement(getSeleniumLocator(loc, locatorText)).getText();
	}

	private void throwIfNull(Object param, String name) {
		if (param == null) {
			throw new IllegalArgumentException(name + " param cannot be null");
		}
	}
}
