package arvato.yaas.service;

import arvato.yaas.data.AddressValidateResponse;
import arvato.yaas.data.AddressVerificationForm;

public interface USPSAddressVerificationService {
	AddressValidateResponse verifyAddressForm(AddressVerificationForm addressForm);
}
