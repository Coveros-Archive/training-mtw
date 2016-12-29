package com.coveros.training.mtw.selenium;

import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.coveros.training.SauceProperties;

public class ResponsiveFirefoxTest extends TargetShoppingCartTest {
	@BeforeClass
	public static void beforeClass() {
		String os = SauceProperties.getString(SauceProperties.OS);
		String geckodriver = "geckodriver";
		if (os.equals("windows")) {
			geckodriver += ".exe";
		}
		System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver/" + os + "/" + geckodriver);
	}
	@Override
	protected PlatformType getPlatformType() {
		return PlatformType.FIREFOX;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		// TODO Auto-generated method stub
		return DesiredCapabilities.firefox();
	}

}
