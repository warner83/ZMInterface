package it.zm.xml;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.NodeList;

public class DataCameras extends DataManagement {

	public DataCameras(String baseUrl, HttpClient cl) {
		super(baseUrl, cl);
	}

	@Override
	protected String getOperationSuffix() {
		return "?skin=xml";
	}

	public int getNumCameras(){
		if( !init )
			return 0; // I still have to fetch data
		
		String expression = "/ZM_XML/MONITOR_LIST/MONITOR";
		
		try {
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
						
			return nodeList.getLength();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return 0;
		}
		
	}
}
