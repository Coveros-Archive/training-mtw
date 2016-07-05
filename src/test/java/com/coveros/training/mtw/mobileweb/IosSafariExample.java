package com.coveros.training.mtw.mobileweb;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;

/**
 * This is an example of how to run a mobile web app test using Appium and
 * Safari on the iOS Simulator
 * <p>
 * The Selenium steps were recorded using Firefox SeleniumIDE
 * <p>
 * Prerequisites/Instructions:
 * <p>
 * <li>Only works on a Mac
 * <li>Install latest version of XCode
 * <li>Start Appium in iOS mode; default settings should be OK
 * <li>Run this test.
 * 
 * @author brian
 *
 */
public final class IosSafariExample extends MobileWebTestExample {

	private static final String DEVICE_NAME = "iPhone 6";

	@Override
	protected PlatformType getPlatformType() {
		return PlatformType.LOCAL;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3");
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		return desiredCapabilities;
	}

}
