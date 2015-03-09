package xmlrpc.程序員雜誌.downloader;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class 程序員雜誌Messages {
	private static final String BUNDLE_NAME = "xmlrpc.程序員雜誌.downloader.config"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private 程序員雜誌Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
