/**
 * 
 */
package xmlrpc.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.AsyncCallback;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import xmlrpc.微信热文.Const;

/**
 * @author jack
 *
 */
public class BlogSender {
	public static void main(String[] args) throws ClientProtocolException,
			IOException, XmlRpcException, InterruptedException {

		Article article = new Article();
		article.setTitle("Test Title");
		article.setContent("Test Content");
		article.setCategory("Test11 Category");
		// Main.xmlrpcApi=http://localhost/ols/xmlrpc.php
		// Main.xmlRpcPassword=123456
		// Main.xmlrpcUserName=universsky

		String xmlRpcApi = "http://localhost/ols/xmlrpc.php";
		String xmlRpcUsername = "universsky";
		String xmlRpcPassword = "123456";
		Param p = new Param(null, null, null, 0, 0, null, null, null, 0, 0,
				null, null, xmlRpcApi, xmlRpcUsername, xmlRpcPassword, null,
				null);
		String blogid = sendBlog(article, p);

	}

	/**
	 * 
	 * @param blogid
	 * @param category
	 * @param p
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */

	public static void newCategory(String blogid, String category, Param p)
			throws MalformedURLException, XmlRpcException {
		String xmlrpcApi = p.getXmlRpcApi();
		String username = p.getXmlRpcUsername();
		String password = p.getXmlRpcPassword();

		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(xmlrpcApi));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		Map<String, String> post = new HashMap<String, String>();
		post.put("categories", "C1");

		Object[] params = new Object[] { blogid, username, password, post };
		client.execute("wp.newCategory", params);
		// client.executeAsync("wp.newCategory", params, new EchoCallback());

	}

	/**
	 * 
	 * @param article
	 * @param xmlRpc
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws XmlRpcException
	 * @throws InterruptedException
	 */
	public static String sendBlog(Article article, Param p)
			throws ClientProtocolException, IOException, XmlRpcException,
			InterruptedException {
		String title = article.getTitle();
		String content = article.getContent();
		String cat = article.getCategory();
		String xmlrpcApi = p.getXmlRpcApi();
		String username = p.getXmlRpcUsername();
		String password = p.getXmlRpcPassword();
		// Set up XML-RPC connection to server
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(xmlrpcApi));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		// Set up parameters required by newPost method

		Map<String, String> catMap = new HashMap<String, String>();
		catMap.put("name", cat);
		catMap.put("slug", cat);
		catMap.put("parent_id", "0");
		catMap.put("description", cat);
		Object[] catParams = new Object[] { "default", username, password,
				catMap };
		Integer catResult = (Integer) client.execute("wp.newCategory",
				catParams);
		System.out.println(catResult);

		Map<String, String> post = new HashMap<String, String>();
		post.put("title", title);
		post.put("description", content);

		Object[] params = new Object[] { "default", username, password, post,
				Boolean.TRUE };

		// Call newPost
		String result = (String) client.execute("metaWeblog.newPost", params);

		updateRelationshipById(result, String.valueOf(catResult));
		// client.executeAsync("metaWeblog.newPost", params, new
		// EchoCallback());

		Thread.sleep(new Random().nextInt(3) * 1000);
		System.out.println(" Created with blogid " + result);
		return result;
	}

	/**
	 * 
	 * @param url
	 * @param startPage
	 * @param endPage
	 * @param param
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws XmlRpcException
	 * @throws InterruptedException
	 */
	public static void sendUseDriverGet(String url, int startPage, int endPage,
			Param param) throws ClientProtocolException, IOException,
			XmlRpcException, InterruptedException {
		int articleCount = 0;
		for (int i = startPage; i <= endPage; i++) {
			String pageUrl = url + i + ".html";
			System.out.println("page:" + (i) + " " + pageUrl);

			String html = Tools.driverGet(pageUrl);
			System.out.println(html);
			ArrayList<String[]> articleUrlsTitles = BlogsDownloader
					.getArticleUrlTitleByHtml(html, param);
			System.out.println(articleUrlsTitles.size());

			for (String[] urltitle : articleUrlsTitles) {
				System.out.println(urltitle[0] + " " + urltitle[1]);
			}
			for (String[] articleUrlTitle : articleUrlsTitles) {

				String articleUrl = articleUrlTitle[0];
				// String articleTitle = articleUrlTitle[1];
				// getTitleByArticleContent
				String articleContent = Tools.driverGet(articleUrl);
				String articleTitle = BlogsDownloader.getTitleByArticleContent(
						articleContent, param);

				String content = BlogsDownloader.getArticleContentUseDriverGet(
						articleUrl, param.getArticleContentStart(),
						param.getArticleContentEnd());

				System.out.println("page: " + i + " article:"
						+ (articleCount++) + " " + articleUrl + "\n"
						+ articleTitle + "\n" + content);

				if (!"Out Of Index".equals(content) && !"NULL".equals(content)) {
					Article article = new Article();
					article.setUrl(articleUrl);
					article.setTitle(articleTitle + param.getBlogSEOTitle());
					article.setContent(content + param.getBlogSEOFooter());

					sendBlog(article, param);
				}
			}

		}
	}

	/**
	 * 
	 * @param url
	 * @param startPage
	 * @param endPage
	 * @param param
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws XmlRpcException
	 * @throws InterruptedException
	 */

	public static void sendUseHttpGet(Param param)
			throws ClientProtocolException, IOException, XmlRpcException,
			InterruptedException {

		for (int i = 4; i <= 19; i++) {
			for (int j = 1; j <= 10; j++) {
				String pageUrl = "http://weixin.sogou.com/pcindex/pc/pc_" + i
						+ "/" + j + ".html";
				System.out.println("page:" + (i) + " " + pageUrl);

				// String html = Tools.driverGet(pageUrl);
				String html = Tools.httpGet(pageUrl);

				// System.out.println(html);
				ArrayList<String[]> articleUrlsTitles = BlogsDownloader
						.getArticleUrlTitleByHtml(html, param);
				System.out.println(articleUrlsTitles.size());

				for (String[] urltitle : articleUrlsTitles) {
					System.out.println(urltitle[0] + " " + urltitle[1]);
				}
				for (String[] articleUrlTitle : articleUrlsTitles) {

					String articleUrl = articleUrlTitle[0];
					// String articleTitle = articleUrlTitle[1];
					// getTitleByArticleContent
					// String articleContent = Tools.driverGet(articleUrl);
					String articleContent = Tools.httpGet(articleUrl);

					String articleTitle = BlogsDownloader
							.getTitleByArticleContent(articleContent, param);

					String content = BlogsDownloader
							.getArticleContentUseDriverGet(articleUrl,
									param.getArticleContentStart(),
									param.getArticleContentEnd());

					// System.out.println("page: " + i + " article:"
					// + (articleCount++) + " " + articleUrl + "\n"
					// + articleTitle + "\n" + content);

					if (!"Out Of Index".equals(content)
							&& !"NULL".equals(content)) {
						Article article = new Article();
						article.setUrl(articleUrl);
						article.setTitle(articleTitle + param.getBlogSEOTitle());
						article.setContent(content + param.getBlogSEOFooter());
						article.setCategory(Const.TIMESTAMP);
						// sendBlog(article, param);
					}
				}

			}
		}

	}

	public static void sendUseDriverGet(Param param)
			throws ClientProtocolException, IOException, XmlRpcException,
			InterruptedException {

		for (int i = 0; i <= 19; i++) {
			for (int j = 1; j <= 10; j++) {
				String pageUrl = "http://weixin.sogou.com/pcindex/pc/pc_" + i
						+ "/" + j + ".html";
				System.out.println("page:" + (i) + " " + pageUrl);

				String html = Tools.driverGet(pageUrl);
				// System.out.println(html);
				ArrayList<String[]> articleUrlsTitles = BlogsDownloader
						.getArticleUrlTitleByHtml(html, param);
				System.out.println(articleUrlsTitles.size());

				for (String[] urltitle : articleUrlsTitles) {
					System.out.println(urltitle[0] + " " + urltitle[1]);
				}
				for (String[] articleUrlTitle : articleUrlsTitles) {

					String articleUrl = articleUrlTitle[0];
					// String articleTitle = articleUrlTitle[1];
					// getTitleByArticleContent
					String articleContent = Tools.driverGet(articleUrl);
					String articleTitle = BlogsDownloader
							.getTitleByArticleContent(articleContent, param);

					String content = BlogsDownloader
							.getArticleContentUseDriverGet(articleUrl,
									param.getArticleContentStart(),
									param.getArticleContentEnd());

					// System.out.println("page: " + i + " article:"
					// + (articleCount++) + " " + articleUrl + "\n"
					// + articleTitle + "\n" + content);

					if (!"Out Of Index".equals(content)
							&& !"NULL".equals(content)) {
						Article article = new Article();
						article.setUrl(articleUrl);
						article.setTitle(articleTitle + param.getBlogSEOTitle());
						article.setContent(content + param.getBlogSEOFooter());
						article.setCategory(Const.TIMESTAMP);

						sendBlog(article, param);
					}
				}

			}
		}

	}

	private static String updateRelationshipById(String blogid, String termid) {
		// 連接數據庫
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://localhost:3306/ols?useUnicode=true&amp;characterEncoding=utf8";

		// MySQL配置时的用户名
		String user = "root";

		// MySQL配置时的密码
		String password = "";

		try {
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			java.sql.Connection conn = DriverManager.getConnection(url, user,
					password);
			conn.setAutoCommit(false);// Disables auto-commit.
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句
			Statement statement = conn.createStatement();

			// 要执行的SQL语句
			String sql = "update ols_term_relationships set term_taxonomy_id="
					+ termid + " where object_id=" + blogid;
			System.out.println(sql);
			// 结果集
			int rs = statement.executeUpdate(sql);
			System.out.println(rs);
			statement.close();
			conn.close();

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return blogid;

	}
}

class EchoCallback implements AsyncCallback {

	public void handleResult(XmlRpcRequest pRequest, Object pResult) {
		System.out.println("Server returns: " + (String) pResult);

	}

	public void handleError(XmlRpcRequest pRequest, Throwable pError) {
		System.out.println("Error occurs: " + pError.getMessage());

	}
}

class WpCategory {
	String name;
	String slug;
	String parent_id;
	String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
