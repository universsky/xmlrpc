package xmlrpc.cnblogs.downloader;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CnBLogsMessages {
	private static final String BUNDLE_NAME = "xmlrpc.cnblogs.downloader.cnblogs-messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private CnBLogsMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
