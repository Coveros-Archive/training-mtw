package com.coveros.training.mtw.mobileweb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

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
 * version. Drag and drop the APK onto the virtual device or use 'adb install'
 * <li>Start Appium in Android mode. All default settings should be fine.
 * <li>Start the virtual device. Ensure there is only one device running.
 * <li>Run this test
 * 
 * 
 * @author brian
 *
 */
public final class AndroidChromeExample extends MobileWebTestExample {

	// Change this if using a different virtual device.
	private static final String DEVICE_NAME = "Samsung Galaxy S6 - 6.0.0 - API 23 - 1440x2560";
	
	@Override
	protected PlatformType getPlatformType() {
		return PlatformType.LOCAL;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		return capabilities;
	}
	
}
