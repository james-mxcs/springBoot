package arvato.yaas.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import arvato.yaas.data.AddressFormValidationResult;
import arvato.yaas.data.AddressValidateResponse;
import arvato.yaas.data.AddressVerificationForm;
import arvato.yaas.service.AddressFormValidationService;
import arvato.yaas.service.USPSAddressVerificationService;

@Controller
public class AddressVerificationController {

	@Resource(name = "uSPSAddressVerificationService")
	private USPSAddressVerificationService uSPSAddressVerificationService;

	@Resource(name = "addressFormValidationService")
	private AddressFormValidationService addressFormValidationService;


	public USPSAddressVerificationService getuSPSAddressVerificationService() {
		return uSPSAddressVerificationService;
	}

	public void setuSPSAddressVerificationService(
			final USPSAddressVerificationService uSPSAddressVerificationService) {
		this.uSPSAddressVerificationService = uSPSAddressVerificationService;
	}

	public AddressFormValidationService getAddressFormValidationService()
	{
		return addressFormValidationService;
	}

	public void setAddressFormValidationService(final AddressFormValidationService addressFormValidationService)
	{
		this.addressFormValidationService = addressFormValidationService;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(final String message)
	{
		this.message = message;
	}

	@Value("${application.message}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(final Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "welcome";
	}

	@RequestMapping(value = "/addressForm", method = RequestMethod.GET)
	public String addressForm()
	{
		return "addressForm";
	}

	@RequestMapping(value = "/submitAddress", method = RequestMethod.POST)
	public String handleSubmit(@ModelAttribute("addressForm") final AddressVerificationForm addressForm, final ModelMap model)
			throws SAXException, ParserConfigurationException
	{
		final AddressFormValidationResult formResult = getAddressFormValidationService().validateForm(addressForm);

		if (formResult.getErrorMessages().size() != 0)
		{
			model.put("errorMessages", formResult.getErrorMessages());
			return "error";
		}

		model.put("address1", addressForm.getAddress1());
		model.put("address2", addressForm.getAddress2());
		model.put("city", addressForm.getCity());
		model.put("state", addressForm.getState());
		model.put("zipcode5", addressForm.getZipcode5());

		AddressValidateResponse result = null;
		result = uSPSAddressVerificationService.verifyAddressForm(addressForm);

		if (!(result.isSuccess()))
		{
			final ArrayList<String> errorMessages = new ArrayList<>();
			errorMessages.add(result.getErrorMessage());
			model.put("errorMessages", errorMessages);
			return "error";
		}

		model.put("result", result.getAddressList());
		return "displayForm";
	}
}
