package cdb.webApp.controllers;

import app.projetCdb.services.ValidatorFormException;
import cdb.binding.ComputerDto;

public interface IFormEditComputerValidator {
	public boolean validate(ComputerDto dto);

	void isValidEditForm(String computerName, String introduced, String discontinued, long idCompany)
			throws ValidatorFormException;
}
