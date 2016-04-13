package com.coveros.training.mtw.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TargetShoppingCartTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://www.target.com";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testTargetSelenium() throws Exception {
		driver.get(baseUrl + "/");
		// ERROR: Caught exception [ERROR: Unsupported command
		// [deleteAllVisibleCookies | | ]]
		driver.findElement(By.id("searchTerm")).clear();
		driver.findElement(By.id("searchTerm")).sendKeys("speakers");
		driver.findElement(By.id("goSearch")).click();
		if (!waitForElement(By.linkText("Jensen Bluetooth Wireless Stereo Speaker - Black..."))){
			fail();
			return;
		}
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver
						.findElement(
								By.linkText("Jensen Bluetooth Wireless Stereo Speaker - Black..."))
						.isDisplayed()) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Thread.sleep(1000);
		}
		driver.findElement(
				By.linkText("Jensen Bluetooth Wireless Stereo Speaker - Black..."))
				.click();
		if (waitForElement(By.cssSelector("button.plus"))) {
			driver.findElement(By.cssSelector("button.plus")).click();
			driver.findElement(By.cssSelector("button.plus")).click();

			driver.findElement(By.id("addToCart")).click();
			for (int second = 0;; second++) {
				if (second >= 60)
					fail("timeout");
				try {
					if (driver
							.findElement(
									By.xpath("//div[@id='addtocart']/div/div/div[2]/h3"))
							.getText().matches("^cart summary[\\s\\S]*$"))
						break;
				} catch (Exception e) {
					e.printStackTrace();
				}
				Thread.sleep(1000);
			}

			assertEquals(
					"1  item added to cart",
					driver.findElement(
							By.xpath("//div[@id='addtocart']/div/div/div/div/h2"))
							.getText());
			try {
				assertEquals(
						"3",
						driver.findElement(By.id("cartUpdatedQty_cartItem0001"))
								.getAttribute("value"));
			} catch (Error e) {
				verificationErrors.append(e.toString());
			}
		}
	}

	private boolean waitForElement(By by) {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
				break;
			}
			try {
				if (driver.findElement(by).isDisplayed()) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Wait interrupted: " + e.getMessage());
			}
		}
		return false;
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			System.err.println(verificationErrorString);
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
