package com.coveros.training.mtw.selenium;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.coveros.training.mtw.selenium.TargetShoppingCartTest.PlatformType;

public class ResponsiveFirefoxTest extends TargetShoppingCartTest {

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
