package com.coveros.training.mtw.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coveros.training.SauceProperties;
import com.saucelabs.common.SauceOnDemandAuthentication;

@RunWith(Parameterized.class)
public class TestThreeSpeakersSauceLabs {
	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the
	 * supplied user name/access key. To use the authentication supplied by
	 * environment variables or from an external file, use the no-arg
	 * {@link SauceOnDemandAuthentication} constructor.
	 */
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
			SauceProperties.getString(SauceProperties.USER_NAME),
			SauceProperties.getString(SauceProperties.ACCESS_KEY));
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private int numSpeakers;

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
	private String sessionId;

	public TestThreeSpeakersSauceLabs(int speakersToFind) {
		this.numSpeakers = speakersToFind;
		this.os = "Windows 8.1"; //$NON-NLS-1$
		this.version = "11"; //$NON-NLS-1$
		this.browser = "internet explorer"; //$NON-NLS-1$
	}

	@Parameters
	public static Collection<Integer[]> getNumberOfSpeakers() {
		return Arrays.asList(new Integer[][] { { 3 }, { 4 } });
	}

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		if (version != null) {
			capabilities.setCapability(CapabilityType.VERSION, version);
		}
		capabilities.setCapability(CapabilityType.PLATFORM, os);
		capabilities.setCapability("name", "Sauce Sample Test");
		this.driver = new RemoteWebDriver(new URL("http://" + authentication.getUsername() + ":"
				+ authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
		this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
		baseUrl = "http://www.target.com/"; //$NON-NLS-1$
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testThreeSpeakers() throws Exception {
		driver.get(baseUrl + "/");
		// ERROR: Caught exception [ERROR: Unsupported command
		// [deleteAllVisibleCookies | | ]]
		driver.findElement(By.id("searchTerm")).clear();
		driver.findElement(By.id("searchTerm")).sendKeys("speakers");
		driver.findElement(By.id("goSearch")).click();
		driver.findElement(By.cssSelector("div.tileInfo > #prodTitle-slp_medium-1-2")).click();
		String nameOfSpeaker = driver.findElement(By.cssSelector("span.fn")).getText();
		for (int i = 1; i < numSpeakers - 1; i++) {
			driver.findElement(By.cssSelector("button.plus")).click();
		}
		driver.findElement(By.cssSelector("button.plus")).click();
		driver.findElement(By.id("addToCart")).click();
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}
			try {
				if (driver.findElement(By.xpath("//div[@id='addtocart']/div/div/div[2]/h3")).getText()
						.matches("^cart summary[\\s\\S]*$")) {
					break;
				}
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		assertEquals("1  item added to cart", //$NON-NLS-1$
				driver.findElement(By.xpath("//div[@id='addtocart']/div/div/div/div/h2")).getText()); //$NON-NLS-1$
		assertEquals(nameOfSpeaker, driver.findElement(By.xpath("//div[2]/ul/li/div[2]/a")).getText()); //$NON-NLS-1$
		try {
			assertEquals(Integer.toString(numSpeakers),
					driver.findElement(By.id("cartUpdatedQty_cartItem0001")).getAttribute("value")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) { //$NON-NLS-1$
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
}
