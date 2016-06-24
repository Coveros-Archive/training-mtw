package com.coveros.training.mtw.appium;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;

@RunWith(Parameterized.class)
public class ParameterizedIosExample extends MobileWebTestExample {

	private String device;
	private String platformVersion;

	public ParameterizedIosExample(String device, String platformVersion) {
		this.device = device;
		this.platformVersion = platformVersion;
	}

	@Parameters
	public static Collection<String[]> getParameters() {
		return Arrays.asList(new String[][] { { "iPhone 5", "9.3" }, { "iPhone 5", "8.1" }, { "iPhone 4s", "9.3" },
				{ "iPad Pro", "9.3" } });

	}

	@Override
	protected PlatformType getPlatformType() {
		return PlatformType.IOS;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, this.platformVersion);
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, this.device);
		return desiredCapabilities;
	}

}
