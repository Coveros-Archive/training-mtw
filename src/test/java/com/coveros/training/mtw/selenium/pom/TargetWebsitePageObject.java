package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;

/**
 * Superclass for all Target page objects.
 * 
 * @author brian
 *
 */
public abstract class TargetWebsitePageObject {
	private SeleniumMobileHelper selenium;
	private TargetWebsitePageObjectFactory factory;

	public TargetWebsitePageObject(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory) {
		this.setSelenium(selenium);
		this.setFactory(factory);
	}

	/**
	 * @return the selenium
	 */
	protected SeleniumMobileHelper getSelenium() {
		return selenium;
	}

	/**
	 * @param selenium
	 *            the selenium to set
	 */
	private void setSelenium(SeleniumMobileHelper selenium) {
		this.selenium = selenium;
	}

	/**
	 * @return the factory
	 */
	protected TargetWebsitePageObjectFactory getFactory() {
		return factory;
	}

	/**
	 * @param factory
	 *            the factory to set
	 */
	private void setFactory(TargetWebsitePageObjectFactory factory) {
		this.factory = factory;
	}
}
