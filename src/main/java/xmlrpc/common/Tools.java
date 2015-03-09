/**
 * 
 */
package xmlrpc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author jack
 *
 */
public class Tools {

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		String url = "http://blog.jobbole.com/all-posts/";
		// System.out.println(httpGet(url));
		System.out.println(driverGet(url));
	}

	public static String GBK2UTF8(String gbk)
			throws UnsupportedEncodingException {

		String iso = new String(gbk.getBytes("UTF-8"), "ISO-8859-1");
		for (byte b : iso.getBytes("ISO-8859-1")) {
			System.out.print(b + " ");
		}

		// 模拟UTF-8编码的网站显示
		return new String(iso.getBytes("ISO-8859-1"), "UTF-8");

	}

	/**
	 * httpget
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpGet2(String url) throws ClientProtocolException,
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
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数 url
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String httpGet(String url) {
		String result = "";
		BufferedReader in = null;
		HttpURLConnection connection = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			int timeout = 30000;
			connection.setConnectTimeout(timeout);
			connection.connect();

			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			// in = new BufferedReader(new InputStreamReader(
			// connection.getInputStream(), "gb2312"));

			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));

			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	// /**
	// * httpget
	// *
	// * @throws IOException
	// * @throws ClientProtocolException
	// */
	// public static String httpGet(String url) throws ClientProtocolException,
	// IOException {
	// String result = "";
	// HttpGet request = new HttpGet(url);
	// HttpResponse response = HttpClients.createDefault().execute(request);
	// System.out.println(response.getStatusLine().getStatusCode());
	// if (response.getStatusLine().getStatusCode() == 200) {
	// result = EntityUtils.toString(response.getEntity());
	// System.out.println(result);
	// }
	// return result;
	//
	// }

	/**
	 * 
	 * @param regex
	 * @param matchContent
	 * @return
	 */
	public static Matcher regexgMatch(String regex, String matchContent) {

		Pattern pArticle = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		// 用Pattern类的matcher()方法生成一个Matcher对象
		Matcher matcher = pArticle.matcher(matchContent);
		return matcher;
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	public static String driverGet(String url)
			throws UnsupportedEncodingException {

		String html = null;

		// 打开指定路径的
		System.setProperty(
				"webdriver.firefox.bin",
				"D:\\XMLRPC\\Firefox_Portable_33.1.1\\FirefoxPortable\\App\\Firefox\\firefox.exe");
		// System.setProperty("webdriver.ie.driver",
		// "C:\\Program Files\\Internet Explorer\\iexplore.exe");

		// 设置访问ChromeDriver的路径
		// System.setProperty(
		// "webdriver.chrome.driver",
		// "C:\\Users\\chenguangjian\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
		//
		// ChromeOptions chromeOptions = new ChromeOptions();
		//
		// chromeOptions.addArguments("--test-type", "--start-maximized",
		// "--ignore-certificate-errors");

		// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		//
		// capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		// WebDriver driver = new ChromeDriver(capabilities);

		FirefoxProfile profile = new FirefoxProfile(
				new File(
						"D:\\XMLRPC\\Firefox_Portable_33.1.1\\FirefoxPortable\\Data\\profile\\"));
		WebDriver driver = new FirefoxDriver(profile);

		// WebDriver driver = new InternetExplorerDriver();

		try {

			driver.get(url);

			Thread.sleep(2000);
			// 获取当前网页源码
			html = driver.getPageSource();// 打印网页源码
			// driver.wait(); //java.lang.IllegalMonitorStateException
			// System.out.println(html);

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

	public static void wait4About1Min() {
		int randInt = (int) (new Random().nextInt(20));
		long start = System.currentTimeMillis();
		// System.out.println("START: " + start);
		long end = System.currentTimeMillis() + (50 + randInt) * 1000;
		// System.out.println("END: " + end);
		while (System.currentTimeMillis() < end) {
			;
		}
	}

}
