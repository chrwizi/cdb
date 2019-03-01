package app.projetCdb.services.validators;

import java.util.Date;

import app.projetCdb.exceptions.InconsistentDateException;
import app.projetCdb.exceptions.InconsistentNameException;
import app.projetCdb.persistance.dto.ComputerDto;

public class FormEditComputerValidator implements IFormEditComputerValidator {

	@Override
	public boolean validate(ComputerDto dto) {
		return false;
	}
	
	public boolean  isValidEditForm(String computerName,String  introduced,String  discontinued,long idCompany) throws InconsistentNameException {
		
		 if(introduced!=null && discontinued!=null) {
			 //verification of consistent of dates 
			 Date introducedDate=new Date();
			 Date discontinuedDate=new Date();
			 if(introducedDate.after(discontinuedDate))throw new InconsistentDateException();
		 }
		 if(computerName==null) throw new InconsistentNameException();
		
		return false;
	}

}
