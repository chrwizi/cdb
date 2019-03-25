package cdb.webApp.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import app.projetCdb.services.ValidatorCause;
import app.projetCdb.services.ValidatorFormException;
import cdb.binding.ComputerDto;

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
