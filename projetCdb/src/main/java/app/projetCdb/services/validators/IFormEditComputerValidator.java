package app.projetCdb.services.validators;

import app.projetCdb.exceptions.ValidatorFormException;
import app.projetCdb.persistance.dto.ComputerDto;

public interface IFormEditComputerValidator {
	public boolean validate(ComputerDto dto);

	void isValidEditForm(String computerName, String introduced, String discontinued, long idCompany)
			throws ValidatorFormException;
}
