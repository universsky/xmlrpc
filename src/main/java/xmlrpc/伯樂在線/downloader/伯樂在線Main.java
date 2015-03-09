/**
 * 
 */
package xmlrpc.伯樂在線.downloader;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;

import xmlrpc.common.Param;

/**
 * @author jack
 *
 */
public class 伯樂在線Main {
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

		xmlrpc.common.BlogSender.sendUseDriverGet(url, 66, 351, param);
	}

	/**
	 * wordpress http://localhost/ols/xmlrpc.php
	 */
	public final static String xmlrpcApi = 伯樂在線Messages
			.getString("Main.xmlrpcApi"); //$NON-NLS-1$
	public final static String username = 伯樂在線Messages
			.getString("Main.xmlrpcUserName"); //$NON-NLS-1$
	public final static String password = 伯樂在線Messages
			.getString("Main.xmlRpcPassword"); //$NON-NLS-1$

	public static final String BLOG_SEO_FOOTER_KEYWORDS = 伯樂在線Messages
			.getString("Main.BlogSEOFooter"); //$NON-NLS-1$
	public static final String BLOG_SEO_TITLE_KEYWORDS = 伯樂在線Messages
			.getString("Main.BlogSEOTitle"); //$NON-NLS-1$

	/**
	 * 每页文章列表的url
	 */
	public final static String url = 伯樂在線Messages
			.getString("Main.blogsPageUrl"); //$NON-NLS-1$

	/**
	 * article Url
	 */
	public static final String articleUrlRegex = 伯樂在線Messages
			.getString("Main.articleUrlRegex"); //$NON-NLS-1$
	public static final String articleUrlStartIndex = 伯樂在線Messages
			.getString("Main.articleUrlStartIndex"); //$NON-NLS-1$
	public static final String articleUrlEndIndex = 伯樂在線Messages
			.getString("Main.articleUrlEndIndex"); //$NON-NLS-1$
	public static final int articleUrlStartIndexOffset = 0;
	public static final int articleUrlEndIndexOffset = 2;
	/**
	 * article Title
	 */
	public static final String articleTitleRegex = 伯樂在線Messages
			.getString("Main.articleTitleRegex"); //$NON-NLS-1$
	public static final String articleTitleStartIndex = 伯樂在線Messages
			.getString("Main.articleTitleStartIndex"); //$NON-NLS-1$
	public static final String articleTitleEndIndex = 伯樂在線Messages
			.getString("Main.articleTitleEndIndex"); //$NON-NLS-1$
	public static final int articleTitletartIndexOffset = 伯樂在線Messages
			.getString("Main.articleTitleStartIndexOffset").length(); //$NON-NLS-1$
	public static final int articleTitleEndIndexOffset = 0;

	/**
	 * article Content
	 */
	public static final String articleContentStart = 伯樂在線Messages
			.getString("Main.articleContentStart"); //$NON-NLS-1$
	public static final String articleContentEnd = 伯樂在線Messages
			.getString("Main.articleContentEnd"); //$NON-NLS-1$

}
