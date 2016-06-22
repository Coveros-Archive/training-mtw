package com.coveros.training.mtw.appium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.MarionetteDriverManager;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

/**
 * This is an example of how to run a mobile web app test using appium and a
 * Genymotion Samsung Galaxy 6 Emulator.
 * <p>
 * The Selenium steps were recorded using Firefox SeleniumIDE
 * <p>
 * Prerequisites/Instructions:
 * <p>
 * <li>Install Genymotion
 * <li>Install the "Samsung Galaxy S6 - 6.0.0 - API 23 - 1440x2560" virtual
 * device
 * <li>Install Chrome on the virtual device.
 * (http://www.apkmirror.com/apk/google-inc/chrome/); Install the latest x86
 * version.
 * <li>Start Appium in Android mode
 * <li>Start the virtual device
 * <li>Run this test
 * 
 * 
 * @author brian
 *
 */
public class AndroidChromeExample {
	private WebDriver driver;
	private String baseUrl;

	private StringBuffer verificationErrors = new StringBuffer();

	// Default Selenium timeout for "wait" commands (in seconds)
	private static int TIMEOUT = 10;

	// Change this if using a different virtual device.
	private static final String DEVICE_NAME = "Samsung Galaxy S6 - 6.0.0 - API 23 - 1440x2560";

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver<>(url, capabilities);
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
