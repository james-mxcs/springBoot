package arvato.yaas.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import arvato.yaas.data.AddressValidateResponse;
import arvato.yaas.data.AddressVerificationData;
import arvato.yaas.data.AddressVerificationForm;
import arvato.yaas.service.USPSAddressVerificationService;

@Service("uSPSAddressVerificationService")
public class USPSAddressVerificationServiceImpl implements USPSAddressVerificationService{
	private static final Logger LOG = Logger.getLogger(USPSAddressVerificationServiceImpl.class);
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String xmlFormat = "<AddressValidateRequest USERID=\"%s\"><Address ID=\"0\"><Address1>%s</Address1><Address2>%s</Address2><City>%s</City><State>%s</State><Zip5>%s</Zip5><Zip4></Zip4></Address></AddressValidateRequest>";


	@Override
	public List<AddressVerificationData> verifyAddressForm(final AddressVerificationForm addressForm) {
		final String xmlParameter = buildXmlFromObject(addressForm);
		final String requestURL = new StringBuilder("http://production.shippingapis.com/ShippingAPITest.dll?API=Verify&XML=").append(xmlParameter).toString();

		String response = null;

		try
		{
			response = getResponseFromServer(requestURL);
		}
		catch (final IOException e)
		{
			LOG.error(e);
		}



		List<AddressVerificationData> list = null;

		list = convertResponseToDataObject(response);


		return list;
	}

	private String buildXmlFromObject(final AddressVerificationForm addressForm){
		final String temp = String.format(xmlFormat, addressForm.getUspsID(), addressForm.getAddress1(), addressForm.getAddress2(), addressForm.getCity(), addressForm.getState(), addressForm.getZipcode5());
//		try {
//			return URLEncoder.encode(temp, "iso-8859-1");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return temp;
	}

	private String getResponseFromServer(final String requestURL) throws ClientProtocolException, IOException{
		URI uri = null;
		URL url = null;
		try {
		      url = new URL(requestURL);
		      final String nullFragment = null;
		      uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), nullFragment);
		      System.out.println("URI " + uri.toString() + " is OK");
		      System.out.println("url path " + url.getPath());
		      System.out.println("url query " + url.getQuery());
		      System.out.println("url " + url.toString());
		}
		catch (final MalformedURLException | URISyntaxException e)
		{
			LOG.error(e);
		      System.out.println("URL " + requestURL + " is a malformed URL");
		    }

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet request = new HttpGet(uri);

		request.addHeader("User-Agent", USER_AGENT);
		final HttpResponse response = client.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + uri);
		System.out.println("Response Code : "
	                + response.getStatusLine().getStatusCode());

		final BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		final StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println("The response is " + result.toString());
		return result.toString();
	}

	private List<AddressVerificationData> convertResponseToDataObject(final String response)
	{

		final InputStream is = new ByteArrayInputStream(response.getBytes());

		JAXBContext jaxbContext;
		try
		{
			jaxbContext = JAXBContext.newInstance(AddressValidateResponse.class);
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			final AddressValidateResponse addressData = (AddressValidateResponse) jaxbUnmarshaller.unmarshal(is);
			return addressData.getAddressList();
		}
		catch (final JAXBException e)
		{
			LOG.error(e);
		}

		return new ArrayList<AddressVerificationData>();


	}

}
