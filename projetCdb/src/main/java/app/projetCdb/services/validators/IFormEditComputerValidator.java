package app.projetCdb.services.validators;

import app.projetCdb.persistance.dto.ComputerDto;

public interface IFormEditComputerValidator {
	public boolean validate(ComputerDto dto);
}
