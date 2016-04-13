package com.coveros.training;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SauceProperties {
	private static final String BUNDLE_NAME = "com.coveros.training.sauce"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private SauceProperties() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
