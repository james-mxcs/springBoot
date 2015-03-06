package arvato.yaas.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddressValidateResponse")
public class AddressValidateResponse
{
	private ArrayList<AddressVerificationData> addressList;

	public ArrayList<AddressVerificationData> getAddressList()
	{
		return addressList;
	}

	 // override the name for the XML element
	@XmlElement(name = "Address")
	public void setAddressList(final ArrayList<AddressVerificationData> addressList)
	{
		this.addressList = addressList;
	}
}
