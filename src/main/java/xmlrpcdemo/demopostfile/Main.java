/**
 * 
 */
package xmlrpcdemo.demopostfile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;

/**
 * @author jack
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws XmlRpcException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException, XmlRpcException, InterruptedException {
		for (int i = 241; i <= 341; i++) {
			String url = Constant.BLOG_URL + i;
			String title = Utils.getTitle(url)
					+ Constant.BLOG_SEO_TITLE_KEYWORDS;
			if (title != null) {
				String content = Utils.getContentByPreffixAndSuffix(url,
						Constant.ARTICLE_PREFIX, Constant.ARTICLE_SUFFIX)
						+ Constant.BLOG_SEO_FOOTER_KEYWORDS;

				Utils.wait1Min();
				DemoPostFile.sendBlogByUrlTiltleContent(url, title, content);

				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				System.out.println(i + " " + timestamp + " " + title);
				Utils.wait1Min();

			}

		}
	}
}
