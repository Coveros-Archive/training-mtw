package com.coveros.training.mtw.appium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;
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
	
	private static final String DEVICE_NAME = "iPhone 5";
	
	@Override
	protected PlatformType getPlatformType() {
		return PlatformType.IOS;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
		return desiredCapabilities;
	}

}
