package arvato.yaas.service;

import arvato.yaas.data.AddressFormValidationResult;
import arvato.yaas.data.AddressVerificationForm;

public interface AddressFormValidationService
{

	AddressFormValidationResult validateForm(AddressVerificationForm addressForm);
}
