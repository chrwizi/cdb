package app.projetCdb.persistance.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;

public class MapperComputer implements IMapperComputerDto {
	private SimpleDateFormat computerDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	private SimpleDateFormat dtoDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Computer mapDto(ComputerDto dto) {
		if (dto == null)
			return null;
		Date introduced = null;
		Date discontinued = null;
		try {
			introduced = computerDateFormat.parse(dto.getIntroduced().concat(" 0:0:0.0"));
			discontinued = computerDateFormat.parse(dto.getDiscontinued().concat(" 0:0:0.0"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Computer(dto.getId(), dto.getName(), introduced, discontinued,
				new Company(dto.getCompanyId(), dto.getCompany()));
	}

	@Override
	public ComputerDto mapComputer(Computer computer) {
		if (computer == null)
			return null;
		return new ComputerDto(computer.getId(), computer.getName(),
				(computer.getIntroduced() != null) ? computer.getIntroduced().toString() : "unknow",
				(computer.getDiscontinued() != null) ? computer.getDiscontinued().toString() : "unknow",
				(computer.getCompany() != null) ? computer.getCompany().getName() : "unknown",
				(computer.getCompany() != null) ? computer.getCompany().getId() : 0L);
	}

	@Override
	public List<Computer> mapListDto(List<ComputerDto> listComputers) {
		if (listComputers == null)
			return null;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		for (ComputerDto dto : listComputers) {
			computers.add(mapDto(dto));
		}
		return computers;
	}
 
	@Override
	public List<ComputerDto> mapListComputer(List<Computer> listComputers) {
		if (listComputers == null)
			return null;
		ArrayList<ComputerDto> dto = new ArrayList<>();
		for (Computer computer : listComputers) {
			dto.add(mapComputer(computer));
		}
		return dto;
	}

	public String DateToString() {
		return null;
	}

}
