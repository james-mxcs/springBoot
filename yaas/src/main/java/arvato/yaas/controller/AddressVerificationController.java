package arvato.yaas.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import arvato.yaas.data.AddressVerificationData;
import arvato.yaas.data.AddressVerificationForm;
import arvato.yaas.service.USPSAddressVerificationService;

@Controller
public class AddressVerificationController {
	
	@Resource(name = "uSPSAddressVerificationService")
	private USPSAddressVerificationService uSPSAddressVerificationService;
	
	
	public USPSAddressVerificationService getuSPSAddressVerificationService() {
		return uSPSAddressVerificationService;
	}

	public void setuSPSAddressVerificationService(
			USPSAddressVerificationService uSPSAddressVerificationService) {
		this.uSPSAddressVerificationService = uSPSAddressVerificationService;
	}

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "welcome";
	}
	
	@RequestMapping(value = "/addressForm", method = RequestMethod.GET)
	public String addressForm(Map<String, Object> model) {
		return "addressForm";
	}

	@RequestMapping(value = "/submitAddress", method = RequestMethod.POST)
	public String handleSubmit(@ModelAttribute("addressForm")AddressVerificationForm addressForm, ModelMap model, HttpServletRequest request) throws SAXException, ParserConfigurationException {
		model.put("address1", addressForm.getAddress1());
		model.put("address2", addressForm.getAddress2());
		model.put("city", addressForm.getCity());
		model.put("state", addressForm.getState());
		model.put("zipcode5", addressForm.getZipcode5());
		
		List<AddressVerificationData> result = null;
		result = uSPSAddressVerificationService.verifyAddressForm(addressForm);
		
		model.put("result", result);
		return "displayForm";
	}
}
