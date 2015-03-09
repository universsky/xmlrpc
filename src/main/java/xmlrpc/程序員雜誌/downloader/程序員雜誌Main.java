/**
 * 
 */
package xmlrpc.程序員雜誌.downloader;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;

import xmlrpc.common.Param;

/**
 * @author jack
 *
 */
public class 程序員雜誌Main {
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

		// xmlrpc.common.BlogSender.sendUseHttpGet(url, 1, 46, param);
	}

	/**
	 * wordpress http://localhost/ols/xmlrpc.php
	 */
	public final static String xmlrpcApi = 程序員雜誌Messages
			.getString("Main.xmlrpcApi"); //$NON-NLS-1$
	public final static String username = 程序員雜誌Messages
			.getString("Main.xmlrpcUserName"); //$NON-NLS-1$
	public final static String password = 程序員雜誌Messages
			.getString("Main.xmlRpcPassword"); //$NON-NLS-1$

	public static final String BLOG_SEO_FOOTER_KEYWORDS = 程序員雜誌Messages
			.getString("Main.BlogSEOFooter"); //$NON-NLS-1$
	public static final String BLOG_SEO_TITLE_KEYWORDS = 程序員雜誌Messages
			.getString("Main.BlogSEOTitle"); //$NON-NLS-1$

	/**
	 * 每页文章列表的url
	 */
	public final static String url = 程序員雜誌Messages
			.getString("Main.blogsPageUrl"); //$NON-NLS-1$

	/**
	 * article Url
	 */
	public static final String articleUrlRegex = 程序員雜誌Messages
			.getString("Main.articleUrlRegex"); //$NON-NLS-1$
	public static final String articleUrlStartIndex = 程序員雜誌Messages
			.getString("Main.articleUrlStartIndex"); //$NON-NLS-1$
	public static final String articleUrlEndIndex = 程序員雜誌Messages
			.getString("Main.articleUrlEndIndex"); //$NON-NLS-1$
	public static final int articleUrlStartIndexOffset = 0;
	public static final int articleUrlEndIndexOffset = 2;
	/**
	 * article Title
	 */
	public static final String articleTitleRegex = 程序員雜誌Messages
			.getString("Main.articleTitleRegex"); //$NON-NLS-1$
	public static final String articleTitleStartIndex = 程序員雜誌Messages
			.getString("Main.articleTitleStartIndex"); //$NON-NLS-1$
	public static final String articleTitleEndIndex = 程序員雜誌Messages
			.getString("Main.articleTitleEndIndex"); //$NON-NLS-1$
	public static final int articleTitletartIndexOffset = 程序員雜誌Messages
			.getString("Main.articleTitleStartIndexOffset").length(); //$NON-NLS-1$
	public static final int articleTitleEndIndexOffset = 0;

	/**
	 * article Content
	 */
	public static final String articleContentStart = 程序員雜誌Messages
			.getString("Main.articleContentStart"); //$NON-NLS-1$
	public static final String articleContentEnd = 程序員雜誌Messages
			.getString("Main.articleContentEnd"); //$NON-NLS-1$

}
