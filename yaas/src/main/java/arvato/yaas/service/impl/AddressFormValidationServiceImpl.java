package arvato.yaas.service.impl;

import org.springframework.stereotype.Service;

import arvato.yaas.data.AddressFormValidationResult;
import arvato.yaas.data.AddressVerificationForm;
import arvato.yaas.service.AddressFormValidationService;

@Service("addressFormValidationService")
public class AddressFormValidationServiceImpl implements AddressFormValidationService
{

	protected boolean isValidZipCode(final String zipcode)
	{
		if (zipcode.length() != 5)
		{
			return false;
		}

		try
		{
			Integer.parseInt(zipcode);
		}
		catch (final NumberFormatException e)
		{
			return false;
		}

		return true;
	}

	protected boolean isValidAddress1(final String address1)
	{
		if (address1.length() > 38)
		{
			return false;
		}
		return true;

	}

	protected boolean isValidAddress2(final String address2)
	{
		if (address2.isEmpty() || address2.length() > 38)
		{
			return false;
		}
		return true;
	}

	protected boolean isValidCity(final String city)
	{
		if (city.length() > 15)
		{
			return false;
		}
		return true;
	}

	protected boolean isValidState(final String state)
	{
		if(state.length() > 2){
			return false;
		}
		return true;
	}

	@Override
	public AddressFormValidationResult validateForm(final AddressVerificationForm addressForm)
	{
		final AddressFormValidationResult result = new AddressFormValidationResult();
		if (!isValidAddress2(addressForm.getAddress2()))
		{
			result.addErrorMessage("Address2 cannot be empty or have more than 38 characters!");
		}
		else if ((addressForm.getCity().isEmpty() || addressForm.getState().isEmpty()) && addressForm.getZipcode5().isEmpty())
		{
			result.addErrorMessage("Either City and State, or Zipcode is required!");
		}
		if (!(isValidAddress1(addressForm.getAddress1())))
		{
			result.addErrorMessage("Address1 cannot have more than 38 characters!");
		}
		if(!(isValidCity(addressForm.getCity()))){
			result.addErrorMessage("City cannot have more than 15 characters!");
		}
		if(!(isValidState(addressForm.getState()))){
			result.addErrorMessage("State cannot have more than 2 characters!");
		}
		if (!addressForm.getZipcode5().isEmpty() && !(isValidZipCode(addressForm.getZipcode5())))
		{
			result.addErrorMessage("Zipcode must have 5 digits!");
		}

		return result;

	}

}
