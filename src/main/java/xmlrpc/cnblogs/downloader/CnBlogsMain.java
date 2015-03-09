/**
 * 
 */
package xmlrpc.cnblogs.downloader;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;

import xmlrpc.common.Param;

/**
 * @author jack
 *
 */
public class CnBlogsMain {

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

		// sendUseDriverGet(urlPick, 72);
		// sendUseDriverGet(urlCandidate, 1);
		xmlrpc.common.BlogSender.sendUseDriverGet(url, 1, 200, param);
		// xmlrpc.common.BlogSender.sendUseHttpGet(url, 1, 200, param);

	}

	/**
	 * wordpress http://localhost/ols/xmlrpc.php
	 */
	public final static String xmlrpcApi = CnBLogsMessages
			.getString("Main.xmlrpcApi"); //$NON-NLS-1$
	public final static String username = CnBLogsMessages
			.getString("Main.xmlrpcUserName"); //$NON-NLS-1$
	public final static String password = CnBLogsMessages
			.getString("Main.xmlRpcPassword"); //$NON-NLS-1$

	public static final String BLOG_SEO_FOOTER_KEYWORDS = CnBLogsMessages
			.getString("Main.BlogSEOFooter"); //$NON-NLS-1$
	public static final String BLOG_SEO_TITLE_KEYWORDS = CnBLogsMessages
			.getString("Main.BlogSEOTitle"); //$NON-NLS-1$

	/**
	 * article Url
	 */
	public static final String articleUrlRegex = CnBLogsMessages
			.getString("Main.articleUrlRegex"); //$NON-NLS-1$
	public static final String articleUrlStartIndex = CnBLogsMessages
			.getString("Main.articleUrlStartIndex"); //$NON-NLS-1$
	public static final String articleUrlEndIndex = CnBLogsMessages
			.getString("Main.articleUrlEndIndex"); //$NON-NLS-1$
	public static final int articleUrlStartIndexOffset = 0;
	public static final int articleUrlEndIndexOffset = 2;
	/**
	 * article Title
	 */
	public static final String articleTitleRegex = CnBLogsMessages
			.getString("Main.articleTitleRegex"); //$NON-NLS-1$
	public static final String articleTitleStartIndex = CnBLogsMessages
			.getString("Main.articleTitleStartIndex"); //$NON-NLS-1$
	public static final String articleTitleEndIndex = CnBLogsMessages
			.getString("Main.articleTitleEndIndex"); //$NON-NLS-1$
	public static final int articleTitletartIndexOffset = CnBLogsMessages
			.getString("Main.articleTitletartIndexOffset").length(); //$NON-NLS-1$
	public static final int articleTitleEndIndexOffset = 0;

	/**
	 * article Content
	 */
	public static final String articleContentStart = CnBLogsMessages
			.getString("Main.articleContentStart"); //$NON-NLS-1$
	public static final String articleContentEnd = CnBLogsMessages
			.getString("Main.articleContentEnd"); //$NON-NLS-1$

	/**
	 * 每页文章列表的url
	 */
	public final static String url = CnBLogsMessages
			.getString("Main.cnblogsPageUrl"); //$NON-NLS-1$
	public final static String urlPick = CnBLogsMessages
			.getString("Main.cnblogsPageUrlPick");
	public final static String urlCandidate = CnBLogsMessages
			.getString("Main.cnblogsPageUrlCandidate");
}
