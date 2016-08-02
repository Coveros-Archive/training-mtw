package com.coveros.training.mtw.selenium;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.coveros.training.mtw.selenium.TargetShoppingCartTest.PlatformType;

public class ResponsiveChromeTest extends TargetShoppingCartTest {

	@Override
	protected PlatformType getPlatformType() {
		return PlatformType.CHROME;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		return DesiredCapabilities.chrome();
	}

}
