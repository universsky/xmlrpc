/**
 * 
 */
package xmlrpcdemo.demopostfile;

/**
 * @author jack
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class DemoPostFile {

	public static void main(String[] args) throws Exception {
		int i = 2015;

		String url = Constant.BLOG_URL + i;
		String title = Utils.getTitle(url) + Constant.BLOG_SEO_TITLE_KEYWORDS;
		if (title != null) {
			String content = Utils.getContentByPreffixAndSuffix(url,
					Constant.ARTICLE_PREFIX, Constant.ARTICLE_SUFFIX)
					+ Constant.BLOG_SEO_FOOTER_KEYWORDS;
			DemoPostFile.sendBlogByUrlTiltleContent(url, title, content);
			System.out.println(i + " " + title);
		}

	}

	/**
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	private static byte[] readFromFile(String src) throws Exception {
		byte data[];
		File file = new File(src);
		InputStream in = new FileInputStream(src);
		data = new byte[(int) file.length()];
		in.read(data);
		in.close();
		return data;
	}

	/**
	 * 
	 * @param filename
	 * @param title
	 * @throws Exception
	 */
	private static void sendBlogByFile(String filename, String title)
			throws Exception {
		// filename = "blogtest.txt";
		// title = "Sample Post From a File";

		// Set up XML-RPC connection to server
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(Constant.xmlrpcApi));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		// Read post from file and set up parameters required
		// by newPost method
		String bits = new String(readFromFile(filename));
		System.out.println(bits);
		Map<String, String> post = new HashMap<String, String>();
		post.put("title", title);
		post.put("description", bits);
		Object[] params = new Object[] { "1", Constant.username,
				Constant.password, post, true };

		// Call newPost
		String result = (String) client.execute("metaWeblog.newPost", params);
		System.out.println(" Created with blogid " + result);
	}

	/**
	 * 
	 * @param content
	 * @throws Exception
	 */
	private static void sendBlogByStringConntent(String content)
			throws Exception {

		String title = Utils.getTitle(content);

		// Set up XML-RPC connection to server
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(Constant.xmlrpcApi));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		Map<String, String> post = new HashMap<String, String>();
		post.put("title", title);
		post.put("description", content);
		Object[] params = new Object[] { "1", Constant.username,
				Constant.password, post, true };

		// Call newPost
		String result = (String) client.execute("metaWeblog.newPost", params);
		System.out.println(" Created with blogid " + result);
	}

	private static void sendBlogByUrl(String url)
			throws ClientProtocolException, IOException, XmlRpcException {

		// Set up XML-RPC connection to server
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(Constant.xmlrpcApi));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		String content = Utils.getContentByUrlAndClassName(url,
				Constant.articleClassName);
		String title = Utils.getTitle(content);

		Map<String, String> post = new HashMap<String, String>();
		post.put("title", title);
		post.put("description", content);
		Object[] params = new Object[] { "1", Constant.username,
				Constant.password, post, true };

		// Call newPost
		String result = (String) client.execute("metaWeblog.newPost", params);
		System.out.println(" Created with blogid " + result);
	}

	/**
	 * 
	 * @param url
	 * @param title
	 * @param content
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws XmlRpcException
	 * @throws InterruptedException
	 */
	static void sendBlogByUrlTiltleContent(String url, String title,
			String content) throws ClientProtocolException, IOException,
			XmlRpcException, InterruptedException {

		// Set up XML-RPC connection to server
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(Constant.xmlrpcApi));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		Map<String, String> post = new HashMap<String, String>();
		post.put("title", title);
		post.put("description", content);
		Object[] params = new Object[] { "1", Constant.username,
				Constant.password, post, true };

		// Call newPost
		String result = (String) client.execute("metaWeblog.newPost", params);
		// System.out.println(result);
		// int tryTimes = 0;
		// while (result!=null && tryTimes < 3) {
		// result = (String) client.execute("metaWeblog.newPost", params);
		// long millis = new Random().nextInt(1000);
		// Thread.sleep(millis);
		// tryTimes++;
		// }
		System.out.println(" Created with blogid " + result);
	}

}