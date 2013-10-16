package it.zm.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public abstract class DataManagement {
	private XPath xPath;
	
	// Retrived xml data
	private String xml;
	
	// Url base
	private String url;
	
	public DataManagement(String baseUrl){
		xPath = XPathFactory.newInstance().newXPath();
		url = baseUrl + getOperationSuffix(); // Final URL, base + suffix of the operation
	}	
	
	protected abstract String getOperationSuffix();
	
	public void fetchData(){
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		 
		request.addHeader("User-Agent", "Mozilla");
		HttpResponse response;
		try {
			response = client.execute(request);
			System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());
	 
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			// Set fetched data
			xml = result.toString();
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
