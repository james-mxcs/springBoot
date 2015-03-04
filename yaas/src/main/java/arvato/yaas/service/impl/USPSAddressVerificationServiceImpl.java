package arvato.yaas.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import arvato.yaas.data.AddressVerificationData;
import arvato.yaas.data.AddressVerificationForm;
import arvato.yaas.service.USPSAddressVerificationService;

@Service("uSPSAddressVerificationService")
public class USPSAddressVerificationServiceImpl implements USPSAddressVerificationService{
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String xmlFormat = "<AddressValidateRequest USERID=\"%s\"><Address ID=\"0\"><Address1>%s</Address1><Address2>%s</Address2><City>%s</City><State>%s</State><Zip5>%s</Zip5><Zip4></Zip4></Address></AddressValidateRequest>";


	@Override
	public List<AddressVerificationData> verifyAddressForm(AddressVerificationForm addressForm) {
		String xmlParameter = buildXmlFromObject(addressForm);
		String requestURL = new StringBuilder("http://production.shippingapis.com/ShippingAPITest.dll?API=Verify&XML=").append(xmlParameter).toString();
		
		String response = null;
		try {
			response = getResponseFromServer(requestURL);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<AddressVerificationData> list = null;
		try {
			list = convertResponseToDataObject(response);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	private String buildXmlFromObject(AddressVerificationForm addressForm){
		String temp = String.format(xmlFormat, addressForm.getUspsID(), addressForm.getAddress1(), addressForm.getAddress2(), addressForm.getCity(), addressForm.getState(), addressForm.getZipcode5());
//		try {
//			return URLEncoder.encode(temp, "iso-8859-1");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return temp;
	}
	
	private String getResponseFromServer(String requestURL) throws ClientProtocolException, IOException{
		URI uri = null;
		URL url = null;
		try {
		      url = new URL(requestURL);
		      String nullFragment = null;
		      uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), nullFragment);
		      System.out.println("URI " + uri.toString() + " is OK");
		      System.out.println("url path " + url.getPath());
		      System.out.println("url query " + url.getQuery());
		      System.out.println("url " + url.toString());
		    } catch (MalformedURLException e) {
		      System.out.println("URL " + requestURL + " is a malformed URL");
		    } catch (URISyntaxException e) {
		      System.out.println("URI " + requestURL + " is a malformed URL");
		    }
		 
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(uri);
		
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);
		
		System.out.println("\nSending 'GET' request to URL : " + uri);
		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());
	 
		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));
	 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.out.println("The response is " + result.toString());
		return result.toString();
	}
	
	private List<AddressVerificationData> convertResponseToDataObject(String response) throws SAXException, IOException, ParserConfigurationException{
		Reader reader = new StringReader(response);
		
		SAXBuilder builder = new SAXBuilder();
		
		Document document = null;
		try {
			document = (Document) builder.build(reader);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("Address");
		
		List<AddressVerificationData> result = new ArrayList<AddressVerificationData>(list.size());
		
		for (int i = 0; i < list.size(); i++) {
			 
		   Element node = (Element) list.get(i);
		   
		   AddressVerificationData addressForm = new AddressVerificationData();
		   
		   addressForm.setAddress1(node.getChildText("Address1"));
		   addressForm.setAddress2(node.getChildText("Address2"));
		   addressForm.setCity(node.getChildText("City"));
		   addressForm.setState(node.getChildText("State"));
		   addressForm.setZipcode5(node.getChildText("Zip5"));
		   addressForm.setZipcode4(node.getChildText("Zip4"));
		   
		   result.add(addressForm);
		   System.out.println(node.getChildText("Address1") + " " + node.getChildText("Address2") + " " + node.getChildText("City")+ " " + node.getChildText("State")+ " " + node.getChildText("Zip5")+ " " + node.getChildText("Zip4"));
		}
		
		return result;
	}

}
