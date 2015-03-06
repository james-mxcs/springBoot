package arvato.yaas.data;

import javax.xml.bind.annotation.XmlElement;


public class AddressVerificationData {
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode5;
	private String zipcode4;


	public String getAddress1() {
		return address1;
	}

	@XmlElement(name = "Address1")
	public void setAddress1(final String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}

	@XmlElement(name = "Address2")
	public void setAddress2(final String address2) {
		this.address2 = address2;
	}


	public String getCity() {
		return city;
	}

	@XmlElement(name = "City")
	public void setCity(final String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}

	@XmlElement(name = "State")
	public void setState(final String state) {
		this.state = state;
	}


	public String getZipcode5() {
		return zipcode5;
	}

	@XmlElement(name = "Zip5")
	public void setZipcode5(final String zipcode5) {
		this.zipcode5 = zipcode5;
	}


	public String getZipcode4() {
		return zipcode4;
	}

	@XmlElement(name = "Zip4")
	public void setZipcode4(final String zipcode4) {
		this.zipcode4 = zipcode4;
	}


}
