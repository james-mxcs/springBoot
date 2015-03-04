package arvato.yaas.data;

public class AddressVerificationForm {
	private String uspsID;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode5;
	
	public String getUspsID() {
		return uspsID;
	}
	public void setUspsID(String uspsID) {
		this.uspsID = uspsID;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode5() {
		return zipcode5;
	}
	public void setZipcode5(String zipcode5) {
		this.zipcode5 = zipcode5;
	}
}
