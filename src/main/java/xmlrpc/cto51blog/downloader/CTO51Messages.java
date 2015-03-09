package xmlrpc.cto51blog.downloader;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CTO51Messages {
	private static final String BUNDLE_NAME = "xmlrpc.cto51blog.downloader.config"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private CTO51Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
