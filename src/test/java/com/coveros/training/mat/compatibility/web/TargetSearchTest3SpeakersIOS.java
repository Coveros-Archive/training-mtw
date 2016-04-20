package com.coveros.training.mat.compatibility.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coveros.training.SauceProperties;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@RunWith(ConcurrentParameterized.class)
public class TargetSearchTest3SpeakersIOS implements SauceOnDemandSessionIdProvider {
	private AppiumDriver<MobileElement> driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the
	 * supplied user name/access key. To use the authentication supplied by
	 * environment variables or from an external file, use the no-arg
	 * {@link SauceOnDemandAuthentication} constructor.
	 */
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("brianhicks", "bad45238-c5f2-4fb4-887b-0480c1a2d085");

	/**
	 * JUnit Rule which will mark the Sauce Job as passed/failed when the test
	 * succeeds or fails.
	 */
	@Rule
	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	/**
	 * Represents the browser to be used as part of the test run.
	 */
	private String browser;
	/**
	 * Represents the operating system to be used as part of the test run.
	 */
	private String os;
	/**
	 * Represents the version of the browser to be used as part of the test run.
	 */
	private String version;
	/**
	 * Instance variable which contains the Sauce Job Id.
	 */
	private String sessionId;

	/**
	 * Constructs a new instance of the test. The constructor requires three
	 * string parameters, which represent the operating system, version and
	 * browser to be used when launching a Sauce VM. The order of the parameters
	 * should be the same as that of the elements within the
	 * {@link #browsersStrings()} method.
	 * 
	 * @param os
	 * @param version
	 * @param browser
	 */
	public TargetSearchTest3SpeakersIOS(String os, String version, String browser) {
		super();
		this.os = os;
		this.version = version;
		this.browser = browser;
	}

	/**
	 * @return a LinkedList containing String arrays representing the browser
	 *         combinations the test should be run against. The values in the
	 *         String array are used as part of the invocation of the test
	 *         constructor
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConcurrentParameterized.Parameters
	public static LinkedList browsersStrings() {
		LinkedList browsers = new LinkedList();
		 browsers.add(new String[] { "Windows 8.1", "11", "internet explorer"
		 });
		 browsers.add(new String[] { "Windows XP", "37.0", "firefox" });
		 browsers.add(new String[] { "Linux", "12.15", "opera" });
		 browsers.add(new String[] { "Linux", "34", "chrome" });
		 browsers.add(new String[] { "OSX 10.10", "8", "safari" });
		browsers.add(new String[] { "OS X 10.9", "7.1", "iphone" });
		// browsers.add(new String[] { "Linux", "4.4", "android" });
		return browsers;
	}

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		if (version != null) {
			capabilities.setCapability(CapabilityType.VERSION, version);
		}
		capabilities.setCapability(CapabilityType.PLATFORM, os);
		capabilities.setCapability("name", "Target Three Speakers Test - " + os + ": " + browser + " " + version);
		// this.driver = new RemoteWebDriver(new URL("http://" +
		// authentication.getUsername() + ":"
		// + authentication.getAccessKey() +
		// "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
		this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
		// driver = new FirefoxDriver();

		baseUrl = "http://www.target.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testTargetSearchTest3Speakers() throws Exception {
		driver.get(baseUrl + "/");
		driver.manage().deleteAllCookies();
		driver.findElement(By.id("searchTerm")).clear();
		driver.findElement(By.id("searchTerm")).sendKeys("speakers");
		driver.findElement(By.id("goSearch")).click();
		driver.findElement(By.linkText("SONOS PLAY:1 Wireless HiFi System - Black")).click();
		driver.findElement(By.cssSelector("button.plus")).click();
		driver.findElement(By.cssSelector("button.plus")).click();
		driver.findElement(By.id("addToCart")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if ("1  item added to cart"
						.equals(driver.findElement(By.xpath("//div[@id='addtocart']/div/div/div/div/h2")).getText()))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		assertEquals("cart summary (3 items)",
				driver.findElement(By.xpath("//div[@id='addtocart']/div/div/div[2]/h3")).getText());
		assertEquals("3", driver.findElement(By.id("cartUpdatedQty_cartItem0001")).getAttribute("value"));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	@Override
	public String getSessionId() {
		return this.sessionId;
	}
}
