package it.zm.xml;

import java.util.ArrayList;
import java.util.List;

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
		
		String expression = "/ZM_XML/MONITOR_LIST/MONITOR[ENABLED=1]/ID	";
		
		try {
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
						
			return nodeList.getLength();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return 0;
		}
		
	}
	
	public List getIDs(){
		List<String> ret = new ArrayList<String>();
		
		String expression = "/ZM_XML/MONITOR_LIST/MONITOR[ENABLED=1]/ID	";
		
		try {
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
						
			for(int i = 0; i < nodeList.getLength(); ++i ){
				ret.add(new String(nodeList.item(i).getFirstChild().getNodeValue()));
			}
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
		return ret;
	} 
	
	public List getNames(){
		List<String> ret = new ArrayList<String>();
		
		String expression = "/ZM_XML/MONITOR_LIST/MONITOR[ENABLED=1]/NAME";
		
		try {
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
						
			for(int i = 0; i < nodeList.getLength(); ++i ){
				ret.add(new String(nodeList.item(i).getFirstChild().getNodeValue()));
			}
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
		return ret;
	}
}
