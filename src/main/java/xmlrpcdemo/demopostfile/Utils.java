/**
 * 
 */
package xmlrpcdemo.demopostfile;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author jack
 *
 */
public class Utils {

	public static String getTitle(String url) throws ClientProtocolException,
			IOException {
		String httpResponse = httpGet(url);
		String titleRegex = "<h3 class=\"article-title\">(.*)</h3>";

		String prefix = "<h3 class=\"article-title\">";
		String suffix = "</h3>";
		int prefixLength = prefix.length();

		String title = null;

		Matcher matcher = regexgMatch(titleRegex, httpResponse);

		while (matcher.find()) {
			title = matcher.group();
			// System.out.println(title);
			title = title.substring(title.indexOf(prefix) + prefixLength,
					title.indexOf(suffix));

			// System.out.println(title);
		}

		return title;
	}

	private static Matcher regexgMatch(String titleRegex, String matchContent) {

		Pattern pArticle = Pattern
				.compile(titleRegex, Pattern.CASE_INSENSITIVE);

		// 用Pattern类的matcher()方法生成一个Matcher对象
		Matcher matcher = pArticle.matcher(matchContent);
		return matcher;
	}

	/**
	 * 
	 * @param url
	 * @param articleClassName
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getContentByUrlAndClassName(String url,
			String articleClassName) throws ClientProtocolException,
			IOException {

		String content = HtmlParserUtil.getElementByClassName(url,
				articleClassName);

		String imgSrcA = "src=\"/files/attachments";
		String imgSrcB = "src=\"http://www.taobaotest.com/files/attachments";
		content = content.replace(imgSrcA, imgSrcB);

		return content;

	}

	public static String getContentByPreffixAndSuffix(String url,
			String preffix, String suffix) throws ClientProtocolException,
			IOException {
		String artcileContentWithOKImgSrc = null;

		String result = httpGet(url);
		// System.out.println(result);
		String title = getTitle(url);
		if (title != null) {
			int startIndex = result.indexOf(preffix);
			int endIndex = result.indexOf(suffix);
			// System.out.println(startIndex);
			// System.out.println(endIndex);

			String articleContent = result.substring(startIndex, endIndex);
			String imgSrcA = "src=\"/files/attachments";
			String imgSrcB = "src=\"http://www.taobaotest.com/files/attachments";
			artcileContentWithOKImgSrc = articleContent.replace(imgSrcA,
					imgSrcB);
		}
		// System.out.println(articleContent);
		return artcileContentWithOKImgSrc;

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

	public static void main(String[] args) throws ClientProtocolException,
			IOException, InterruptedException {
		// String url = "http://www.taobaotest.com/blogs/800";

		// String title = getTitle(url) + Constant.BLOG_SEO_TITLE_KEYWORDS;
		// String content = getContentByUrlAndClassName(url,
		// Constant.articleClassName) + Constant.BLOG_SEO_FOOTER_KEYWORDS;
		// System.out.println("TITLE: " + title);
		// System.out.println("CONNTENT: " + content);

		// System.out.println(getContentByPreffixAndSuffix(url,
		// Constant.ARTICLE_PREFIX, Constant.ARTICLE_SUFFIX));

		wait1Min();

	}

	public static void wait1Min() {
		int randInt = (int) (new Random().nextInt(10)) + 10;
		long start = System.currentTimeMillis();
		// System.out.println("START: " + start);
		long end = System.currentTimeMillis() + (60 + randInt) * 1000;
		// System.out.println("END: " + end);
		while (System.currentTimeMillis() < end) {
			;
		}
		// System.out.println("CURRENT: " + System.currentTimeMillis());
	}
}
