package com.coveros.training.mtw.appium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

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

	private static int TIMEOUT = 10;

	public enum PlatformType {
		IOS, ANDROID
	}

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
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		switch (getPlatformType()) {
		case IOS:
			driver = new IOSDriver<>(url, desiredCapabilities);
			break;
		case ANDROID:
			driver = new AndroidDriver<>(url, desiredCapabilities);
			break;
		}
		baseUrl = "http://www.target.com/";
	}

	@Test
	public void testTargetMobileThreeSpeakersNew() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.id("searchLabel")).click();
		driver.findElement(By.id("search")).clear();
		driver.findElement(By.id("search")).sendKeys("speakers");
		driver.findElement(By.xpath("(//button[@id='searchReset'])[2]")).click();
		for (int second = 0;; second++) {
			if (second >= TIMEOUT)
				fail("timeout");
			try {
				if ("“speakers”".equals(
						driver.findElement(By.xpath("//div[@id='slp-facet-wrap']/section/div/div/h1")).getText()))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.xpath("(//a[contains(text(),'Bose 251 Environmental Outdoor Speaker Sys...')])[2]"))
				.click();
		for (int second = 0;; second++) {
			if (second >= TIMEOUT)
				fail("timeout");
			try {
				if ("Bose 251 Environmental Outdoor Speaker System - Black (24653)"
						.equals(driver.findElement(By.cssSelector("p.details--title")).getText()))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		new Select(driver.findElement(By.id("sbc-quantity-picker"))).selectByVisibleText("2");
		driver.findElement(By.xpath("//div[@id='AddToCartAreaId']/div/div/button")).click();
		for (int second = 0;; second++) {
			if (second >= TIMEOUT)
				fail("timeout");
			try {
				if ("added to cart".equals(driver
						.findElement(By.xpath("//div[@id='block-ATC']/div[2]/div/div[2]/div/div/div[2]/h2")).getText()))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.xpath("//button[@type='button']")).click();
		for (int second = 0;; second++) {
			if (second >= TIMEOUT)
				fail("timeout");
			try {
				if ("cart total: $831.98"
						.equals(driver.findElement(By.xpath("//section[@id='cart-page']/div/div/h1")).getText()))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		assertEquals("Bose 251 Environmental Outdoor Speaker System - Black (24653)", driver
				.findElement(By.linkText("Bose 251 Environmental Outdoor Speaker System - Black (24653)")).getText());
		assertEquals("$799.98", driver.findElement(By.cssSelector("span.cartItem--price")).getText());
		driver.findElement(By.xpath("//div/div[2]/div/button")).click();
		for (int second = 0;; second++) {
			if (second >= TIMEOUT)
				fail("timeout");
			try {
				if ("remove this item from your cart?"
						.equals(driver.findElement(By.xpath("//div[@id='basicModal']/div[2]/div/div/h2")).getText())) {
					break;
				}

			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.xpath("//div[2]/div/div[2]/button")).click();
		for (int second = 0;; second++) {
			if (second >= TIMEOUT)
				fail("timeout");
			try {
				if ("your cart is empty".equals(driver.findElement(By.cssSelector("h1.title-text.alpha")).getText()))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		assertEquals("your cart is empty", driver.findElement(By.cssSelector("h1.title-text.alpha")).getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
