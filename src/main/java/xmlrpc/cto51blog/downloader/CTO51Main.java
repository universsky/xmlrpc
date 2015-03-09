/**
 * 
 */
package xmlrpc.cto51blog.downloader;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;

import xmlrpc.common.Param;

/**
 * @author jack
 *
 */
public class CTO51Main {


	/**
	 * wordpress http://localhost/ols/xmlrpc.php
	 */
	public final static String xmlrpcApi = CTO51Messages
			.getString("Main.xmlrpcApi"); //$NON-NLS-1$
	public final static String username = CTO51Messages
			.getString("Main.xmlrpcUserName"); //$NON-NLS-1$
	public final static String password = CTO51Messages
			.getString("Main.xmlRpcPassword"); //$NON-NLS-1$

	public static final String BLOG_SEO_FOOTER_KEYWORDS = CTO51Messages
			.getString("Main.BlogSEOFooter"); //$NON-NLS-1$
	public static final String BLOG_SEO_TITLE_KEYWORDS = CTO51Messages
			.getString("Main.BlogSEOTitle"); //$NON-NLS-1$

	/**
	 * 每页文章列表的url
	 */
	public final static String url = CTO51Messages
			.getString("Main.blogsPageUrl"); //$NON-NLS-1$
	public final static String url2 = CTO51Messages
			.getString("Main.blogsPageUrl2");
	/**
	 * article Url
	 */
	public static final String articleUrlRegex = CTO51Messages
			.getString("Main.articleUrlRegex"); //$NON-NLS-1$
	public static final String articleUrlStartIndex = CTO51Messages
			.getString("Main.articleUrlStartIndex"); //$NON-NLS-1$
	public static final String articleUrlEndIndex = CTO51Messages
			.getString("Main.articleUrlEndIndex"); //$NON-NLS-1$
	public static final int articleUrlStartIndexOffset = 0;
	public static final int articleUrlEndIndexOffset = 2;
	/**
	 * article Title
	 */
	public static final String articleTitleRegex = CTO51Messages
			.getString("Main.articleTitleRegex"); //$NON-NLS-1$
	public static final String articleTitleStartIndex = CTO51Messages
			.getString("Main.articleTitleStartIndex"); //$NON-NLS-1$
	public static final String articleTitleEndIndex = CTO51Messages
			.getString("Main.articleTitleEndIndex"); //$NON-NLS-1$
	public static final int articleTitletartIndexOffset = CTO51Messages
			.getString("Main.articleTitleStartIndexOffset").length(); //$NON-NLS-1$
	public static final int articleTitleEndIndexOffset = 0;

	/**
	 * article Content
	 */
	public static final String articleContentStart = CTO51Messages
			.getString("Main.articleContentStart"); //$NON-NLS-1$
	public static final String articleContentEnd = CTO51Messages
			.getString("Main.articleContentEnd"); //$NON-NLS-1$

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws InterruptedException
	 * @throws XmlRpcException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException, XmlRpcException, InterruptedException {

		Param param = new Param(articleUrlRegex, articleUrlStartIndex,
				articleUrlEndIndex, articleUrlStartIndexOffset,
				articleUrlEndIndexOffset, articleTitleRegex,
				articleTitleStartIndex, articleTitleEndIndex,
				articleTitletartIndexOffset, articleTitleEndIndexOffset,
				articleContentStart, articleContentEnd, xmlrpcApi, username,
				password, BLOG_SEO_TITLE_KEYWORDS, BLOG_SEO_FOOTER_KEYWORDS);

		xmlrpc.common.BlogSender.sendUseDriverGet(url, 1, 30, param);

	}

}
