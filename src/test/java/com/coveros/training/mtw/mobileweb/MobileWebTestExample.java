package com.coveros.training.mtw.mobileweb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coveros.training.SauceProperties;
import com.coveros.training.mtw.mobileweb.SeleniumMobileHelper.Locator;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.saucerest.SauceREST;

/**
 * Abstract class providing a simple example that demonstrates that the same
 * selenium test code can be executed against either an Android or an iOS
 * device.
 * <p>
 * This class provides the test implementation and through required abstract
 * methods allows subclasses to define the platform and device type through
 * {@link DesiredCapabilities}
 * 
 * @author brian
 *
 */
public abstract class MobileWebTestExample {
	private WebDriver driver;
	private String baseUrl;

	private StringBuffer verificationErrors = new StringBuffer();

	public enum PlatformType {
		LOCAL, SAUCE
	}

	private String itemName = "Innovative Technology Premium Bluetooth";

	private String itemType = "speakers";

	private int itemCount = 2;

	private String itemCountStr = null;

	private String sessionId;

	private SauceREST sauceRestApi;

	/**
	 * Helper class that completely encapsulates Selenium calls
	 */
	private SeleniumMobileHelper selenium;

	/**
	 * Return the platform, either IOS or ANDROID, corresponding to the
	 * operating system on which the test should run.
	 * 
	 * @return
	 */
	protected abstract PlatformType getPlatformType();

	/**
	 * Return the capabilities of the device on which the tests should run.
	 * 
	 * @return
	 */
	protected abstract DesiredCapabilities getCapabilities();

	@Before
	public final void setUp() throws Exception {
		// setup the web driver and launch the webview app.
		DesiredCapabilities desiredCapabilities = getCapabilities();

		switch (getPlatformType()) {
		case LOCAL:
			URL localUrl = new URL("http://127.0.0.1:4723/wd/hub");
			driver = new RemoteWebDriver(localUrl, desiredCapabilities);
			break;
		case SAUCE:
			SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
					SauceProperties.getString(SauceProperties.USER_NAME),
					SauceProperties.getString(SauceProperties.ACCESS_KEY));
			this.driver = new RemoteWebDriver(new URL("http://" + authentication.getUsername() + ":"
					+ authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), desiredCapabilities);

			sauceRestApi = new SauceREST(SauceProperties.getString(SauceProperties.USER_NAME),
					SauceProperties.getString(SauceProperties.ACCESS_KEY));
			this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
			break;
		}
		baseUrl = "http://www.target.com/";
		selenium = new SeleniumMobileHelper(driver, baseUrl);
		itemCountStr = new Integer(itemCount).toString();
	}

	private void failTest(String message) {
		verificationErrors.append(message);
		fail("timeout");
		if (sauceRestApi != null) {
			sauceRestApi.jobFailed(this.sessionId);
		}
	}

	@Test
	public void testTargetMobileThreeSpeakersNew() throws Exception {
		selenium.tapElement(Locator.ID, "searchLabel");
		selenium.clearField(Locator.ID, "search");
		selenium.typeTextInto(Locator.ID, "search", itemType);
		selenium.tapElement(Locator.XPATH, "(//button[@id='searchReset'])[2]");

		String expectedText = "“" + itemType + "”";

		if (!selenium.isTextInElementWithExplicitWait(Locator.XPATH, "//div[@id='slp-facet-wrap']/section/div/div/h1",
				expectedText)) {
			failTest("timeout");
		}

		selenium.tapElement(Locator.PARTIAL_LINK, itemName);
		if (!selenium.isTextInElementWithExplicitWait(Locator.CSS, "p.details--title", itemName)) {
			failTest("timeout");
		}

		selenium.selectFromVisibleChoices(Locator.ID, "sbc-quantity-picker", itemCountStr);
		selenium.tapElement(Locator.XPATH, "//div[@id='AddToCartAreaId']/div/div/button");
		if (!selenium.isTextInElementWithExplicitWait(Locator.CSS, "h2.itemRtText.h-standardSpacingLeft",
				itemCountStr + " added to cart")) {
			failTest("timeout");
		}

		selenium.tapElement(Locator.XPATH, "//button[@type='button']");
		if (!selenium.isTextInElementWithExplicitWait(Locator.XPATH, "//section[@id='cart-page']/div/div/h1",
				"cart total:")) {
			failTest("timeout");
		}

		assertTrue(selenium.getElementText(Locator.PARTIAL_LINK, itemName).startsWith(itemName));
		selenium.tapElement(Locator.XPATH, "//div/div[2]/div/button");
		if (!selenium.isTextInElementWithExplicitWait(Locator.XPATH, "//div[@id='basicModal']/div[2]/div/div/h2",
				"remove this item from your cart?")) {
			failTest("timeout");
		}

		selenium.tapElement(Locator.XPATH, "//div[2]/div/div[2]/button");
		if (!selenium.isTextInElementWithExplicitWait(Locator.CSS, "h1.title-text.alpha", "your cart is empty")) {
			failTest("Timeout or unexpected text looking for empty cart message");
		}

		assertEquals("your cart is empty", selenium.getElementText(Locator.CSS, "h1.title-text.alpha"));

	}

	@After
	public void tearDown() throws Exception {

		driver.close();
		driver.quit();
		if (this.sauceRestApi != null) {
			sauceRestApi.jobPassed(this.sessionId);
		}
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			failTest(verificationErrorString);
		}
	}
}
