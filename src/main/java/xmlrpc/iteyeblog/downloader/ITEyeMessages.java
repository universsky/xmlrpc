package xmlrpc.iteyeblog.downloader;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ITEyeMessages {
	private static final String BUNDLE_NAME = "xmlrpc.iteyeblog.downloader.config"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private ITEyeMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
