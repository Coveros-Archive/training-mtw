package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;

/**
 * Page object representing the home page for Target's web site (http://target.com)
 * 
 * @author brian
 *
 */
public final class TargetHomePage extends TargetWebsiteSearchablePage {

	public static final TargetHomePage newInstance(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory) {
		return new TargetHomePage(selenium, factory);
	}

	private TargetHomePage(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory) {
		super(selenium, factory);
	}

}
