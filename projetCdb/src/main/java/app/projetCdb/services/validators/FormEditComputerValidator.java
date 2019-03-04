package app.projetCdb.services.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import app.projetCdb.exceptions.ValidatorFormException;
import app.projetCdb.exceptions.ValidatorCause; 
import app.projetCdb.persistance.dto.ComputerDto;

public class FormEditComputerValidator implements IFormEditComputerValidator {
	DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
	@Override
	public boolean validate(ComputerDto dto) {
		return false;
	}
	
	@Override
	public void  isValidEditForm(String computerName,String  introduced,String  discontinued,long idCompany) throws ValidatorFormException {
		
		 if(introduced!=null && discontinued!=null) {
			 //verification of consistent of dates 
			 LocalDate introducedDate=LocalDate.parse(introduced, dateFormat);
			 LocalDate discontinuedDate=LocalDate.parse(discontinued, dateFormat);
			 if(introducedDate.isAfter(discontinuedDate)) throw new ValidatorFormException("dates are not consistents", ValidatorCause.INCONSITENT_DATES);
		 }
		 if(computerName.equals("")) throw new ValidatorFormException("Empty name",ValidatorCause.EMPTY_NAME);
	}

}  
