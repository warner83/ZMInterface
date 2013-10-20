package it.zm.xml;

import org.apache.http.client.HttpClient;

public class DataEvents extends DataManagement {

	private String ID;
	private String page;
	private String numEvents;
	
	public DataEvents(String baseUrl, HttpClient cl) {
		super(baseUrl, cl);
	}

	public void setRequestParams(String i, String p, String n){
		ID = i;
		page = p;
		numEvents = n;
	}
	
	@Override
	protected String getOperationSuffix() {
		return "?skin=xml&numEvents="+numEvents+"&Id="+ID+"&pageOff="+page;
	}
	
	

}
