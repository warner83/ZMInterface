package it.zm.xml;

import it.zm.data.MonitorEvent;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.client.HttpClient;
import org.w3c.dom.NodeList;

public class DataEvents extends DataManagement {

	private String ID;
	private String page;
	private String numEvents;
	
	public DataEvents(String baseUrl, HttpClient cl, String id) {
		super(baseUrl, cl);
		
		ID = id;
		page = new String("0");
		numEvents = new String("10");
	}

	public void setNumEvents(String e){
		numEvents = e;
	}
	
	public void setPage(String p){
		page = p;
	}
	
	@Override
	protected String getOperationSuffix() {
		return "?skin=xml&numEvents="+numEvents+"&Id="+ID+"&pageOff"+ID+"="+page;
	}
	
	public List getAllEvents(){
		List<MonitorEvent> ret = new ArrayList<MonitorEvent>();
		
		String expression = "/ZM_XML/MONITOR_LIST/MONITOR[ID="+ID+"]/EVENTS/EVENT";
		
		try {
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
						
			for(int i = 0; i < nodeList.getLength(); ++i ){
				
				ret.add(new MonitorEvent(nodeList.item(i)));
			}
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
		return ret;
	}

}
