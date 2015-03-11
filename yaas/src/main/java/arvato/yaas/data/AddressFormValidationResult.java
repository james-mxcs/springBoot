package arvato.yaas.data;

import java.util.ArrayList;

public class AddressFormValidationResult
{
	private ArrayList<String> errorMessages;
	private boolean success;

	public AddressFormValidationResult()
	{
		errorMessages = new ArrayList<>();
	}

	public ArrayList<String> getErrorMessages()
	{
		return errorMessages;
	}

	public void setErrorMessages(final ArrayList<String> errorMessages)
	{
		this.errorMessages = errorMessages;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(final boolean success)
	{
		this.success = success;
	}

	public void addErrorMessage(final String errorMessage)
	{
		errorMessages.add(errorMessage);
	}

}
