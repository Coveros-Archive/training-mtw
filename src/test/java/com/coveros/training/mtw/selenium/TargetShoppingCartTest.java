package com.coveros.training.mtw.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coveros.training.SauceProperties;
import com.coveros.training.mtw.selenium.pom.ConfirmRemoveItemDialog;
import com.coveros.training.mtw.selenium.pom.EmptyCartPage;
import com.coveros.training.mtw.selenium.pom.PageLoadException;
import com.coveros.training.mtw.selenium.pom.ProductDetailsPage;
import com.coveros.training.mtw.selenium.pom.SearchResultsPage;
import com.coveros.training.mtw.selenium.pom.ShoppingCartConfirmDialog;
import com.coveros.training.mtw.selenium.pom.ShoppingCartPage;
import com.coveros.training.mtw.selenium.pom.TargetHomePage;
import com.coveros.training.mtw.selenium.pom.TargetWebsitePageObjectFactory;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.saucerest.SauceREST;

import io.github.bonigarcia.wdm.MarionetteDriverManager;

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
public abstract class TargetShoppingCartTest {
	private WebDriver driver;

	private StringBuffer verificationErrors = new StringBuffer();

	public enum PlatformType {
		LOCAL, SAUCE, FIREFOX, CHROME
	}

	// private String speakersProductName = "Innovative Technology Premium
	// Bluetooth";
	private String speakersProductName = "Sonos Compact Smart Speaker For Streaming";
	private String speakersProductType = "speakers";

	private int itemCount = 2;

	private String sessionId;

	private SauceREST sauceRestApi;

	private TargetHomePage homePage;

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

	@BeforeClass
	public static void setupClass() {
		MarionetteDriverManager.getInstance().setup();
	}

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
		case FIREFOX:
			this.driver = new MarionetteDriver(getCapabilities());
			driver.manage().deleteAllCookies();
			driver.manage().window().setSize(new Dimension(375, 1000));
			break;
		case CHROME:
			this.driver = new ChromeDriver(getCapabilities());
			driver.manage().deleteAllCookies();
			driver.manage().window().setSize(new Dimension(375, 1000));
			break;
		}
		TargetWebsitePageObjectFactory factory = TargetWebsitePageObjectFactory.newInstance(driver);
		// Navigate to the default homepage
		homePage = factory.newTargetHomePage();
	}

	private void failTest(String message) {
		verificationErrors.append(message);
		fail(verificationErrors.toString());
		if (sauceRestApi != null) {
			sauceRestApi.jobFailed(this.sessionId);
		}
	}

	@Test
	/**
	 * Ensure that
	 * 
	 * @throws Exception
	 */
	public void testTargetMobileThreeSpeakersNew() throws Exception {
		try {

			SearchResultsPage searchResultsPage = homePage.searchFor(speakersProductType);

			ProductDetailsPage productDetailsPage = searchResultsPage.selectProduct(speakersProductName);
			assertTrue(searchResultsPage.getPageTitle().startsWith(speakersProductName));

			ShoppingCartConfirmDialog cartConfirmDialog = productDetailsPage.addQuantityToCart(itemCount);
			ShoppingCartPage cartPage = cartConfirmDialog.clickViewCartAndCheckOut();

			int actualCountInCart = cartPage.getQuantityInCart(speakersProductName);
			String cartPageSummaryText = cartPage.getCartSummaryText();

			assertEquals(itemCount, actualCountInCart);
			assertNotNull(cartPageSummaryText);
			assertTrue(cartPageSummaryText.startsWith("cart total:"));

			ConfirmRemoveItemDialog removeItemDialog = cartPage.removeItemFromCart(speakersProductName);
			EmptyCartPage emptyCartPage = removeItemDialog.clickRemoveButton();

			assertEquals("your cart is empty", emptyCartPage.getEmptyCartMessageText());

		} catch (PageLoadException ple) {
			// If a page fails to load as it should then this exception will be
			// thrown and we fail the test.
			failTest(ple.getMessage());
		}
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
