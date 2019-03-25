package cdb.webApp.controllers;

import cdb.binding.ComputerDto;
import cdb.service.ValidatorFormException;

public interface IFormEditComputerValidator {
	public boolean validate(ComputerDto dto);

	void isValidEditForm(String computerName, String introduced, String discontinued, long idCompany)
			throws ValidatorFormException;
}
