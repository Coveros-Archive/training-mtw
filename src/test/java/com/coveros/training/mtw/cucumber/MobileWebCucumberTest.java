package com.coveros.training.mtw.cucumber;

import org.openqa.selenium.WebDriver;

import org.junit.Assert;

public abstract class MobileWebCucumberTest {

	public abstract WebDriver getDriver();

	protected final void fail(String msg) {
		Assert.fail(msg);
		if (getDriver() != null) {
			getDriver().close();
		} else {
			System.out.println("Driver was null");
		}
	}

	protected void finishTest() {
		if (getDriver() != null) {
			getDriver().close();
			getDriver().quit();
		} else {
			System.out.println("Tried to quit but driver was null");
		}
	}

	protected final void error(Exception e, String message, boolean quit) throws Exception {

		e.printStackTrace();

		if (quit) {
			finishTest();
			throw e;
		} else {
			e.printStackTrace();
		}

	}

	protected final void error(Exception e, boolean quit) throws Exception {
		error(e, e.getMessage(), quit);
	}

	protected final void error(Exception e, String message) throws Exception {
		this.error(e, message, false);
	}

	protected final void error(Exception e) throws Exception {
		error(e, e.getMessage());
	}
}
