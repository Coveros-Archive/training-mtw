package com.coveros.training.mtw.selenium;

import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.coveros.training.SauceProperties;

public class ResponsiveChromeTest extends TargetShoppingCartTest {
	@BeforeClass
	public static void beforeClass() {
		String os = SauceProperties.getString(SauceProperties.OS);
		String driverName = "chromedriver";
		if (os.equals("windows")) {
			driverName += ".exe";
		}
		System.setProperty("webdriver.chrome.driver", "src/test/resources/" + driverName + "/" + os + "/" + driverName);
	}

	@Override
	protected PlatformType getPlatformType() {
		return PlatformType.CHROME;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		return DesiredCapabilities.chrome();
	}

}
