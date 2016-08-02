package com.coveros.training.mtw.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.coveros.training.mtw.selenium.pom.PageLoadException;
import com.thoughtworks.selenium.webdriven.commands.Click;

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

	public List<WebElement> getElementsWithAttributesMatching(By locator, String attributeName, String attributeValue) {
		List<WebElement> elements = driver.findElements(locator);
		List<WebElement> matches = new ArrayList<>();
		for (WebElement we : elements) {
			String attr = we.getAttribute(attributeName);
			if (attr != null && attr.startsWith(attributeValue)) {
				matches.add(we);
			}
		}
		return matches;
	}

	public List<WebElement> getElementsWithAttributesMatching(Locator locator, String locatorString,
			String attributeName, String attributeValue) {
		return getElementsWithAttributesMatching(getSeleniumLocator(locator, locatorString), attributeName,
				attributeValue);
	}

	public final void setTimeout(int seconds) {
		this.TIMEOUT = seconds;
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public boolean isLocalBrowserTest() {
		return (driver instanceof FirefoxDriver || driver instanceof ChromeDriver
				|| driver instanceof MarionetteDriver);
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
	 * Throw a {@link PageLoadException} if the specified text is not found
	 * within the specified element within a standard 10s timeout
	 * 
	 * @param locator
	 *            the locator type used to find the element
	 * @param locatorString
	 *            the locator string used to find the element
	 * @param value
	 *            the value of the text that should be found in the element
	 * @param message
	 *            the message to put into the exception
	 * @throws PageLoadException
	 *             if the element text is not found in the element identified by
	 *             the locator
	 */
	public final void throwIfTextNotFoundInElement(Locator locator, String locatorString, String value, String message)
			throws PageLoadException {
		if (!isTextInElementWithExplicitWait(locator, locatorString, value)) {
			throw new PageLoadException(message);
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
		return new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.textToBe(by, value));
	}

	public boolean isTextMatchingElementWithExplicitWait(Locator locator, String locatorText, String value) {
		throwIfNull(locator, "locator");
		By by = getSeleniumLocator(locator, locatorText);
		return new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.textMatches(by, Pattern.compile(value)));
	}

	public WebElement isElementPresentWithExplicitWait(Locator locator, String locatorText) {
		By by = getSeleniumLocator(locator, locatorText);

		return new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(by));
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

	public void scrollToClick(Locator locator, String locatorText) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		By selLoc = getSeleniumLocator(locator, locatorText);
		WebDriverWait waitDriver = new WebDriverWait(driver, 5);
		WebElement elementToClick = waitDriver.until(ExpectedConditions.presenceOfElementLocated(selLoc));

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + (elementToClick.getLocation().y) + ")");
		// Click the element
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		elementToClick.click();
	}

	public void waitForElementPresent(Locator locator, String locatorText) {
		new WebDriverWait(driver, TIMEOUT)
				.until(ExpectedConditions.presenceOfElementLocated(getSeleniumLocator(locator, locatorText)));
	}

	public void waitForElementClickable(Locator locator, String locatorText) {

		new WebDriverWait(driver, TIMEOUT)
				.until(ExpectedConditions.elementToBeClickable(getSeleniumLocator(locator, locatorText)));
	}

	public String getPageTitle() {
		return driver.getTitle();
	}
}
