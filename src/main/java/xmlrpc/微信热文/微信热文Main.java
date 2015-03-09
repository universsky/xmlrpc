/**
 * 
 */
package xmlrpc.微信热文;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;

import xmlrpc.common.Param;

/**
 * @author jack
 *
 */
public class 微信热文Main {
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

		xmlrpc.common.BlogSender.sendUseDriverGet(param);
	}

	/**
	 * wordpress http://localhost/ols/xmlrpc.php
	 */
	public final static String xmlrpcApi = 微信热文Messages
			.getString("Main.xmlrpcApi"); //$NON-NLS-1$
	public final static String username = 微信热文Messages
			.getString("Main.xmlrpcUserName"); //$NON-NLS-1$
	public final static String password = 微信热文Messages
			.getString("Main.xmlRpcPassword"); //$NON-NLS-1$

	public static final String BLOG_SEO_FOOTER_KEYWORDS = 微信热文Messages
			.getString("Main.BlogSEOFooter"); //$NON-NLS-1$
	public static final String BLOG_SEO_TITLE_KEYWORDS = 微信热文Messages
			.getString("Main.BlogSEOTitle"); //$NON-NLS-1$

	/**
	 * 每页文章列表的url
	 */
	public final static String url = 微信热文Messages
			.getString("Main.blogsPageUrl"); //$NON-NLS-1$

	/**
	 * article Url
	 */
	public static final String articleUrlRegex = 微信热文Messages
			.getString("Main.articleUrlRegex"); //$NON-NLS-1$
	public static final String articleUrlStartIndex = 微信热文Messages
			.getString("Main.articleUrlStartIndex"); //$NON-NLS-1$
	public static final String articleUrlEndIndex = 微信热文Messages
			.getString("Main.articleUrlEndIndex"); //$NON-NLS-1$
	public static final int articleUrlStartIndexOffset = 0;
	public static final int articleUrlEndIndexOffset = 0;
	/**
	 * article Title
	 */
	public static final String articleTitleRegex = 微信热文Messages
			.getString("Main.articleTitleRegex"); //$NON-NLS-1$
	public static final String articleTitleStartIndex = 微信热文Messages
			.getString("Main.articleTitleStartIndex"); //$NON-NLS-1$
	public static final String articleTitleEndIndex = 微信热文Messages
			.getString("Main.articleTitleEndIndex"); //$NON-NLS-1$
	public static final int articleTitletartIndexOffset = 微信热文Messages
			.getString("Main.articleTitleStartIndexOffset").length(); //$NON-NLS-1$
	public static final int articleTitleEndIndexOffset = 0;

	/**
	 * article Content
	 */
	public static final String articleContentStart = 微信热文Messages
			.getString("Main.articleContentStart"); //$NON-NLS-1$
	public static final String articleContentEnd = 微信热文Messages
			.getString("Main.articleContentEnd"); //$NON-NLS-1$

}
