package arvato.yaas.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddressValidateResponse")
public class AddressValidateResponse
{
	private String errorMessage;

	private boolean success;

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

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(final boolean success)
	{
		this.success = success;
	}

}
