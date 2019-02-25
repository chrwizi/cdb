package app.projetCdb.services.validators;

import app.projetCdb.persistance.dto.ComputerDto;

public class FormEditComputerValidator implements IFormEditComputerValidator {

	@Override
	public boolean validate(ComputerDto dto) {
		return false;
	}

}
