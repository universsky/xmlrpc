/**
 * 
 */
package xmlrpc.qa163blog.downloader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import xmlrpcdemo.demopostfile.Constant;

/**
 * @author jack
 *
 */
public class XmlRpcDownloader {

	/**
	 * wordpress http://localhost/ols/xmlrpc.php
	 */
	public final static String xmlrpcApi = "http://localhost/ols/xmlrpc.php";
	public final static String username = "universsky";
	public final static String password = "15850537705";

	public final static String url = "http://qa.blog.163.com/blog/#m=0";

	// public final static String articleRegex =
	// "<a class=\"fc03 m2a\"(.*)href=\"http://(.*)\">(.*)</a>";
	public final static String articleRegex = "<a href=\"http://qa.blog.163.com/blog/static/[0-9]+/\" target=\"_blank\" title=\"阅读全文\" class=\"fc03 m2a\">";

	public final static String titleRegex = "<h3 class=\"title pre fs1\"><span class=\"tcnt\">(.*)</span>&nbsp;&nbsp;<span class=\"bgc0 fc07 fw0 fs0\"></span></h3>";

	public final static String titlePrefix = "<h3 class=\"title pre fs1\"><span class=\"tcnt\">";
	public final static String titleSuffix = "</span>&nbsp;&nbsp;<span class=\"bgc0 fc07 fw0 fs0\"></span></h3>";

	public final static String blogStart = "<div class=\"nbw-blog-start\"";
	public final static String blogEnd = "<div class=\"nbw-blog-end\"></div>";

	public static final String BLOG_SEO_FOOTER_KEYWORDS = "<br>我们从来只做一件事,分享.<br>"
			+ "让美在这个世界流转<br>"
			+ "让倍感无趣的 受伤的 彷徨的 孤独的 或是心情忧郁的 人生黯淡的人们<br>"
			+ "能有一次机会<br>"
			+ "去再一次发现这个世界的美<br>"
			+ "并把美传递给他人<br>"
			+ "     ---光影人像(Follow WeChat public number with interest)";

	public static final String BLOG_SEO_TITLE_KEYWORDS = " [ 光影人像  东海陈光剑 的博客 ]";

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws InterruptedException
	 * @throws XmlRpcException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException, XmlRpcException, InterruptedException {

		syntheticSend(url, username, password, xmlrpcApi);

		// String articleUrl =
		// "http://qa.blog.163.com/blog/static/190147002201312842948262/";
		// String content = getArticleContentByPreffixAndSuffix(articleUrl,
		// blogStart, blogEnd);
		// System.out.println(content);
		// ArrayList<String> articleUrls = getArticleUrls(url);
		// for (String articleUrl : articleUrls) {
		// String title = getArticleTitle(articleUrl);
		// String content = getArticleContentByPreffixAndSuffix(articleUrl,
		// blogStart, blogEnd);
		// if (title != null) {
		// sendBlogByTiltleContent(title + BLOG_SEO_TITLE_KEYWORDS,
		// content + BLOG_SEO_FOOTER_KEYWORDS);
		// }
		//
		// }
		// System.out.println(articleUrls);
		// System.out
		// .println(getArticleTitle("http://qa.blog.163.com/blog/static/190147002201312842948262/"));

	}

	public static void sendBlogByTiltleContent(String title, String content)
			throws ClientProtocolException, IOException, XmlRpcException,
			InterruptedException {

		// Set up XML-RPC connection to server
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(xmlrpcApi));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		Map<String, String> post = new HashMap<String, String>();
		post.put("title", title);
		post.put("description", content);
		Object[] params = new Object[] { "1", username, password, post, true };

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

	/**
	 * 
	 * @param url
	 * @return
	 */

	public static ArrayList<String> getArticleUrls(String url) {
		ArrayList<String> articleUrls = new ArrayList<String>();
		String html = driverGet(url);
		// System.out.println(html);
		Matcher matcher = regexgMatch(articleRegex, html);
		while (matcher.find()) {
			String articleUrl = matcher.group();
			articleUrl = articleUrl.substring(articleUrl.indexOf("http://"),
					articleUrl.indexOf("target=\"_blank\"") - 2);
			// System.out.println(articleUrl);
			articleUrls.add(articleUrl);
		}

		return articleUrls;
	}

	/**
	 * 
	 * @param html
	 * @return
	 */
	public static ArrayList<String> getArticleUrlsByHtml(String html) {
		ArrayList<String> articleUrls = new ArrayList<String>();
		// System.out.println(html);
		Matcher matcher = regexgMatch(articleRegex, html);
		while (matcher.find()) {
			String articleUrl = matcher.group();
			articleUrl = articleUrl.substring(articleUrl.indexOf("http://"),
					articleUrl.indexOf("target=\"_blank\"") - 2);
			// System.out.println(articleUrl);
			articleUrls.add(articleUrl);
		}

		return articleUrls;
	}

	/**
	 * 
	 * @param articleUrl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getArticleTitle(String articleUrl)
			throws ClientProtocolException, IOException {

		String httpResponse = httpGet(articleUrl);
		// System.out.println(httpResponse);
		int prefixLength = titlePrefix.length();

		String title = null;

		Matcher matcher = regexgMatch(titleRegex, httpResponse);

		while (matcher.find()) {
			title = matcher.group();
			// System.out.println(title);
			title = title.substring(title.indexOf(titlePrefix) + prefixLength,
					title.indexOf(titleSuffix));

			// System.out.println(title);
		}

		return title;
	}

	private static Matcher regexgMatch(String regex, String matchContent) {

		Pattern pArticle = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		// 用Pattern类的matcher()方法生成一个Matcher对象
		Matcher matcher = pArticle.matcher(matchContent);
		return matcher;
	}

	/**
	 * 
	 * @param url
	 * @param start
	 * @param end
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public static String getArticleContentByPreffixAndSuffix(String url,
			String start, String end) throws ClientProtocolException,
			IOException {
		// String artcileContentWithOKImgSrc = null;
		String result = httpGet(url);
		// System.out.println(result);
		String title = getArticleTitle(url);
		// System.out.println(title);
		if (title != null && result != null) {
			int startIndex = result.indexOf(start);
			int endIndex = result.indexOf(end);

			System.out.println(startIndex);
			System.out.println(endIndex);

			if (startIndex != -1 && endIndex != -1) {
				result = result.substring(startIndex, endIndex);
			} else {
				return "Out Of Index";
			}
			// String imgSrcA = "src=\"/files/attachments";
			// String imgSrcB =
			// "src=\"http://www.taobaotest.com/files/attachments";
			// artcileContentWithOKImgSrc = articleContent.replace(imgSrcA,
			// imgSrcB);
		} else {
			return "NULL";
		}
		// System.out.println(articleContent);
		// return artcileContentWithOKImgSrc;
		return result;

	}

	/**
	 * httpget
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpGet(String url) throws ClientProtocolException,
			IOException {
		String result = "";
		HttpGet request = new HttpGet(url);
		HttpResponse response = HttpClients.createDefault().execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(response.getEntity());
			// System.out.println(result);
		}
		return result;

	}

	/**
	 * 
	 * @param url
	 * @return
	 */

	public static String driverGet(String url) {

		String html = null;

		WebDriver driver = new FirefoxDriver();

		try {

			driver.get(url);

			for (int i = 0; i <= 29; i++) {
				Thread.sleep(10000);
				// 获取当前网页源码
				html += driver.getPageSource();// 打印网页源码
				// driver.wait(); //java.lang.IllegalMonitorStateException
				// System.out.println(html);
				String xpath = "//*[@id=\"-1\"]/div[2]/div[1]/div/div[2]/div/div/div[3]/div/span[13]";
				driver.findElement(By.xpath(xpath)).click();
				i++;
			}

		} catch (Exception e) {// 打印堆栈信息
			e.printStackTrace();
		} finally {
			try {// 关闭并退出
				driver.close();
				driver.quit();
			} catch (Exception e) {
			}
		}
		return html;
	}

	public static void syntheticSend(String url, String username,
			String password, String xmlrpcApi) {
		String xpath = "//*[@id=\"-1\"]/div[2]/div[1]/div/div[2]/div/div/div[3]/div/span[13]";

		WebDriver driver = new FirefoxDriver();
		try {

			driver.get(url);

			for (int p = 1; p <= 27; p++) {
				System.out.println(p);
				Thread.sleep(10000);
				driver.findElement(By.xpath(xpath)).click();
			}
			for (int i = 29; i <= 30; i++) {
				System.out.println(i);
				Thread.sleep(10000);
				// 获取当前网页源码
				String html = driver.getPageSource();
				// driver.wait(); //java.lang.IllegalMonitorStateException
				// System.out.println(html);

				ArrayList<String> articleUrls = getArticleUrlsByHtml(html);

				for (String articleUrl : articleUrls) {

					System.out.println(articleUrl);
					String title = getArticleTitle(articleUrl);
					System.out.println(title);

					String content = getArticleContentByPreffixAndSuffix(
							articleUrl, blogStart, blogEnd);
					if (title != null && content != null) {
						sendBlogByTiltleContent(
								title + BLOG_SEO_TITLE_KEYWORDS, content
										+ BLOG_SEO_FOOTER_KEYWORDS, username,
								password, xmlrpcApi);
					}
				}

				driver.findElement(By.xpath(xpath)).click();
				i++;
			}

		} catch (Exception e) {// 打印堆栈信息
			e.printStackTrace();
		} finally {
			try {// 关闭并退出
				driver.close();
				driver.quit();
			} catch (Exception e) {
			}
		}

	}

	private static void sendBlogByTiltleContent(String title, String content,
			String username, String password, String xmlrpcApi)
			throws XmlRpcException, MalformedURLException {
		// Set up XML-RPC connection to server
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(xmlrpcApi));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		Map<String, String> post = new HashMap<String, String>();
		post.put("title", title);
		post.put("description", content);
		Object[] params = new Object[] { "1", username, password, post, true };

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
