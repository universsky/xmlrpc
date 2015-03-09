package xmlrpc.csdnblog.downloader;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CSDNMessages {
	private static final String BUNDLE_NAME = "xmlrpc.csdnblog.downloader.csdnblogs-messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private CSDNMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
