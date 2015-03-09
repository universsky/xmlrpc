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
		// String keyword = "����";
		final String className = "article-body cssbase";
		// extracLinks(url);
		// extractKeyWordText(url, keyword);
		String result = getElementByClassName(url, className);
		System.out.println(result);
	}

	/**
	 * // ѭ���������нڵ㣬��������ؼ��ֵ�ֵ�ڵ�
	 * 
	 * @param url
	 * @param keyword
	 */

	public static void extractKeyWordText(String url, String keyword) {
		try {
			// ����һ����������������ҳ�� url ��Ϊ����
			Parser parser = new Parser(url);
			// ������ҳ�ı���,����ֻ��������һ�� utf-8 ������ҳ
			parser.setEncoding("utf-8");
			// �������нڵ�, null ��ʾ��ʹ�� NodeFilter
			NodeList list = parser.parse(null);
			// �ӳ�ʼ�Ľڵ��б�������еĽڵ�
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

		// ������ʼ
		SimpleNodeIterator iterator = nodelist.elements();
		while (iterator.hasMoreNodes()) {
			Node node = iterator.nextNode();
			// �õ��ýڵ���ӽڵ��б�
			NodeList childList = node.getChildren();
			// ���ӽڵ�Ϊ�գ�˵����ֵ�ڵ�
			if (null == childList) {
				// �õ�ֵ�ڵ��ֵ
				String result = node.toPlainTextString();
				// System.out.println(result);
				// �������ؼ��֣���򵥴�ӡ�����ı�
				if (result.indexOf(keyword) != -1) {
					System.out.println(result);
				}
			} // end if
				// ���ӽڵ㲻Ϊ�գ����������ú��ӽڵ�
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

		// ������ʼ
		SimpleNodeIterator iterator = nodelist.elements();
		while (iterator.hasMoreNodes()) {
			Node node = iterator.nextNode();
			// �õ��ýڵ���ӽڵ��б�
			NodeList childList = node.getChildren();
			// ���ӽڵ�Ϊ�գ�˵����ֵ�ڵ�
			if (null == childList) {
				// �õ�ֵ�ڵ��ֵ
				// result = node.toPlainTextString();
				articleBody.append(node.toHtml());
				// System.out.println(articleBody);

			} // end if
				// ���ӽڵ㲻Ϊ�գ����������ú��ӽڵ�
			else {
				iterateNodeList(childList);
			}// end else
		}// end wile

	}

	/**
	 * // ��ȡһ����ҳ�����е����Ӻ�ͼƬ����
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
			// OrFilter �����ù��� <a> ��ǩ��<img> ��ǩ�� <frame> ��ǩ��������ǩ�� or �Ĺ�ϵ
			OrFilter rOrFilter = new OrFilter(
					new NodeClassFilter(LinkTag.class), new NodeClassFilter(
							ImageTag.class));
			OrFilter linkFilter = new OrFilter(rOrFilter, nodeFilter);
			// �õ����о������˵ı�ǩ
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> ��ǩ
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// url
					String text = link.getLinkText();// ��������
					System.out.println(linkUrl + "  " + text);
				} else if (tag instanceof ImageTag)// <img> ��ǩ
				{
					ImageTag image = (ImageTag) list.elementAt(i);
					System.out.print(image.getImageURL() + "  ");// ͼƬ��ַ
					System.out.println(image.getText());// ͼƬ����
				} else// <frame> ��ǩ
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
	 * // ��ȡclass�����div
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

			// �õ����о������˵ı�ǩ
			NodeList nodelist = parser.extractAllNodesThatMatch(nodeFilter);
			HtmlParserUtil.iterateNodeList(nodelist);

			articleBody = HtmlParserUtil.articleBody.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return articleBody;
	}

}
