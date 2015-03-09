package xmlrpc.微信热文;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class 微信热文Messages {
	private static final String BUNDLE_NAME = "xmlrpc.微信热文.config"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private 微信热文Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
