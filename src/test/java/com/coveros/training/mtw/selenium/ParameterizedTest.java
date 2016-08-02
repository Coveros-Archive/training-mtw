package com.coveros.training.mtw.selenium;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;

/**
 * This class runs the {@link TargetShoppingCartTest} using JUnit parameters
 * rather than separate classes for each platform.
 * <p>
 * This class could be extended to read from a properties file or some other
 * configuration to specify the parameters.
 * 
 * @author brian
 *
 */
@RunWith(Parameterized.class)
public class ParameterizedTest extends TargetShoppingCartTest {

	private PlatformType platformType;
	private DesiredCapabilities caps;

	public ParameterizedTest(PlatformType platformType, DesiredCapabilities caps) {
		this.platformType = platformType;
		this.caps = caps;
	}

	@Parameters
	public static Collection<Object[]> getParameters() {

		return Arrays.asList(new Object[][] { { PlatformType.FIREFOX, DesiredCapabilities.firefox() },
				{ PlatformType.LOCAL, getIosCaps("iPhone 6") },
				{ PlatformType.CHROME, DesiredCapabilities.chrome() } });

	}

	private static DesiredCapabilities getIosCaps(String deviceName) {
		DesiredCapabilities iosCaps = new DesiredCapabilities();
		iosCaps.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
		iosCaps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3");
		iosCaps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		iosCaps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");

		return iosCaps;
	}

	@Override
	protected PlatformType getPlatformType() {
		return this.platformType;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		return this.caps;
	}

}
