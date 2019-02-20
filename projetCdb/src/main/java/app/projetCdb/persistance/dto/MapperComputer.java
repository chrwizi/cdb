package app.projetCdb.persistance.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;

public class MapperComputer implements IMapperComputerDto {
	private SimpleDateFormat computerDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	private SimpleDateFormat dtoDateFormat=new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Computer mapDto(ComputerDto dto) {
		if (dto == null)
			return null;
		
		return new Computer(dto.getId(), dto.getName(), dto.getDiscontinued(), dto.getDiscontinued(),
				new Company(dto.getCompanyId(), dto.getCompagny()));
	}

	
	@Override
	public ComputerDto mapComputer (Computer computer) {
		if (computer == null)
			return null;
/*		Date parsedDate;
		try {
			parsedDate = computerDateFormat.parse(computerDateFormat.format(computer.getIntroduced()));
			System.out.println(parsedDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return new ComputerDto(computer.getId(), computer.getName(), computer.getIntroduced(),
				computer.getDiscontinued(), computer.getCompany().getName(), computer.getCompany().getId());

	}

	@Override
	public List<Computer> mapListDto(List<ComputerDto> listComputers) {
		if(listComputers==null) return null;
		ArrayList<Computer> computers=new ArrayList<Computer>();
		for (ComputerDto dto : listComputers) {
			computers.add(mapDto(dto));
		}
		return computers;
	}

	@Override
	public List<ComputerDto> mapListComputer(List<Computer> listComputers) {
		if(listComputers==null)return null;
		ArrayList<ComputerDto> dto=new ArrayList<>();
		for (Computer computer: listComputers) {
			dto.add(mapComputer(computer));
		}
		return dto;
	}
	
	

}
