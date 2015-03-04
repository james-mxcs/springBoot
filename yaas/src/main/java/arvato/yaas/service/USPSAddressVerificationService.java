package arvato.yaas.service;

import java.util.List;

import arvato.yaas.data.AddressVerificationData;
import arvato.yaas.data.AddressVerificationForm;

public interface USPSAddressVerificationService {
	List<AddressVerificationData> verifyAddressForm(AddressVerificationForm addressForm);
}
