/**
 * 
 */
package xmlrpc.iteyeblog.downloader;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;

import xmlrpc.common.Param;

/**
 * @author jack
 *
 */
public class ITEyeMain {

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
		// xmlrpc.common.BlogSender.sendUseHttpGet(url, 1, 8, param);
		xmlrpc.common.BlogSender.sendUseDriverGet(url, 1, 8, param);
	}

	/**
	 * wordpress http://localhost/ols/xmlrpc.php
	 */
	public final static String xmlrpcApi = ITEyeMessages
			.getString("Main.xmlrpcApi"); //$NON-NLS-1$
	public final static String username = ITEyeMessages
			.getString("Main.xmlrpcUserName"); //$NON-NLS-1$
	public final static String password = ITEyeMessages
			.getString("Main.xmlRpcPassword"); //$NON-NLS-1$

	public static final String BLOG_SEO_FOOTER_KEYWORDS = ITEyeMessages
			.getString("Main.BlogSEOFooter"); //$NON-NLS-1$
	public static final String BLOG_SEO_TITLE_KEYWORDS = ITEyeMessages
			.getString("Main.BlogSEOTitle"); //$NON-NLS-1$

	/**
	 * 每页文章列表的url
	 */
	public final static String url = ITEyeMessages
			.getString("Main.blogsPageUrl"); //$NON-NLS-1$

	/**
	 * article Url
	 */
	public static final String articleUrlRegex = ITEyeMessages
			.getString("Main.articleUrlRegex"); //$NON-NLS-1$
	public static final String articleUrlStartIndex = ITEyeMessages
			.getString("Main.articleUrlStartIndex"); //$NON-NLS-1$
	public static final String articleUrlEndIndex = ITEyeMessages
			.getString("Main.articleUrlEndIndex"); //$NON-NLS-1$
	public static final int articleUrlStartIndexOffset = 0;
	public static final int articleUrlEndIndexOffset = 2;
	/**
	 * article Title
	 */
	public static final String articleTitleRegex = ITEyeMessages
			.getString("Main.articleTitleRegex"); //$NON-NLS-1$
	public static final String articleTitleStartIndex = ITEyeMessages
			.getString("Main.articleTitleStartIndex"); //$NON-NLS-1$
	public static final String articleTitleEndIndex = ITEyeMessages
			.getString("Main.articleTitleEndIndex"); //$NON-NLS-1$
	public static final int articleTitletartIndexOffset = ITEyeMessages
			.getString("Main.articleTitleStartIndexOffset").length(); //$NON-NLS-1$
	public static final int articleTitleEndIndexOffset = 0;

	/**
	 * article Content
	 */
	public static final String articleContentStart = ITEyeMessages
			.getString("Main.articleContentStart"); //$NON-NLS-1$
	public static final String articleContentEnd = ITEyeMessages
			.getString("Main.articleContentEnd"); //$NON-NLS-1$

}
