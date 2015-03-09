/**
 * 
 */
package xmlrpcdemo.demopostfile;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * @author jack
 *
 */
public class HtmlParserUtil {
	public static StringBuffer articleBody = new StringBuffer();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://www.taobaotest.com/blogs/qa?bid=353";
		// String keyword = "测试";
		final String className = "article-body cssbase";
		// extracLinks(url);
		// extractKeyWordText(url, keyword);
		String result = getElementByClassName(url, className);
		System.out.println(result);
	}

	/**
	 * // 循环访问所有节点，输出包含关键字的值节点
	 * 
	 * @param url
	 * @param keyword
	 */

	public static void extractKeyWordText(String url, String keyword) {
		try {
			// 生成一个解析器对象，用网页的 url 作为参数
			Parser parser = new Parser(url);
			// 设置网页的编码,这里只是请求了一个 utf-8 编码网页
			parser.setEncoding("utf-8");
			// 迭代所有节点, null 表示不使用 NodeFilter
			NodeList list = parser.parse(null);
			// 从初始的节点列表跌倒所有的节点
			processNodeList(list, keyword);
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param nodelist
	 * @param keyword
	 */
	private static void processNodeList(NodeList nodelist, String keyword) {

		// 迭代开始
		SimpleNodeIterator iterator = nodelist.elements();
		while (iterator.hasMoreNodes()) {
			Node node = iterator.nextNode();
			// 得到该节点的子节点列表
			NodeList childList = node.getChildren();
			// 孩子节点为空，说明是值节点
			if (null == childList) {
				// 得到值节点的值
				String result = node.toPlainTextString();
				// System.out.println(result);
				// 若包含关键字，则简单打印出来文本
				if (result.indexOf(keyword) != -1) {
					System.out.println(result);
				}
			} // end if
				// 孩子节点不为空，继续迭代该孩子节点
			else {
				processNodeList(childList, keyword);
			}// end else
		}// end wile
	}

	/**
	 * iterate node list
	 * 
	 * @param nodelist
	 */
	private static void iterateNodeList(NodeList nodelist) {

		// 迭代开始
		SimpleNodeIterator iterator = nodelist.elements();
		while (iterator.hasMoreNodes()) {
			Node node = iterator.nextNode();
			// 得到该节点的子节点列表
			NodeList childList = node.getChildren();
			// 孩子节点为空，说明是值节点
			if (null == childList) {
				// 得到值节点的值
				// result = node.toPlainTextString();
				articleBody.append(node.toHtml());
				// System.out.println(articleBody);

			} // end if
				// 孩子节点不为空，继续迭代该孩子节点
			else {
				iterateNodeList(childList);
			}// end else
		}// end wile

	}

	/**
	 * // 获取一个网页上所有的链接和图片链接
	 * 
	 * @param url
	 */

	public static void extracLinks(String url) {
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("utf-8");
			NodeFilter nodeFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node.getText().startsWith("img src=")) {
						return true;
					} else {
						return false;
					}
				}
			};
			// OrFilter 来设置过滤 <a> 标签，<img> 标签和 <frame> 标签，三个标签是 or 的关系
			OrFilter rOrFilter = new OrFilter(
					new NodeClassFilter(LinkTag.class), new NodeClassFilter(
							ImageTag.class));
			OrFilter linkFilter = new OrFilter(rOrFilter, nodeFilter);
			// 得到所有经过过滤的标签
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> 标签
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// url
					String text = link.getLinkText();// 链接文字
					System.out.println(linkUrl + "  " + text);
				} else if (tag instanceof ImageTag)// <img> 标签
				{
					ImageTag image = (ImageTag) list.elementAt(i);
					System.out.print(image.getImageURL() + "  ");// 图片地址
					System.out.println(image.getText());// 图片文字
				} else// <frame> 标签
				{
					String frame = tag.getText();
					int start = frame.indexOf("src=");
					frame = frame.substring(start);
					int end = frame.indexOf(" ");
					if (end == -1)
						end = frame.indexOf(">");
					frame = frame.substring(5, end - 1);
					System.out.println(frame);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * // 获取class下面的div
	 * 
	 * @param url
	 */

	public static String getElementByClassName(String url,
			final String className) {
		String articleBody = null;
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("utf-8");
			NodeFilter nodeFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node.getText().startsWith(
							"div class=\"" + className + "\"")) {
						return true;
					} else {
						return false;
					}
				}
			};

			// 得到所有经过过滤的标签
			NodeList nodelist = parser.extractAllNodesThatMatch(nodeFilter);
			HtmlParserUtil.iterateNodeList(nodelist);

			articleBody = HtmlParserUtil.articleBody.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return articleBody;
	}

}
