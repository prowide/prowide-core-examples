/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Helper class to load file resources used in the examples.
 * 
 * @author www.prowidesoftware.com
 */
public class Resources {
	private static final String BUNDLE_NAME = "com.prowidesoftware.swift.samples.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Resources() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
