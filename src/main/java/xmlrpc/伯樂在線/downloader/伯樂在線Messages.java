package xmlrpc.伯樂在線.downloader;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class 伯樂在線Messages {
	private static final String BUNDLE_NAME = "xmlrpc.伯樂在線.downloader.config"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private 伯樂在線Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
