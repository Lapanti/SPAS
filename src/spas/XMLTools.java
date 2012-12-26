package spas;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLTools {

	public static String getTagValue(String sTag, Element element) {
		NodeList nlList = element.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = nlList.item(0);

		try {
			return nValue.getNodeValue();
		} catch (Exception ex) {
		}
		return null;
	}

}
