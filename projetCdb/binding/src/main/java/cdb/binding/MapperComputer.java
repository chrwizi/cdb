package cdb.binding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cdb.core.models.Company;
import cdb.core.models.Computer;

@Service
public class MapperComputer implements IMapperComputerDto {
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public Computer mapDto(ComputerDto dto) {
		if (dto == null){
			return null;
		}
		LocalDate introduced =null;
		LocalDate discontinued=null;
		try {
		introduced = (dto.getIntroduced()!=null)?LocalDate.parse(dto.getIntroduced(), dateFormatter):null;
		discontinued =(dto.getDiscontinued()!=null)?LocalDate.parse(dto.getDiscontinued(), dateFormatter):null;
		}
		catch (DateTimeParseException e) {
			logger.debug("Date Au mauvais format : " +e.getMessage());
			throw new DateTimeParseException(null, null, (Integer) null);
		}
		return new Computer(dto.getId(), dto.getName(), introduced, discontinued,
				new Company(dto.getCompanyId(), dto.getCompany()));
	}
 
	@Override
	public ComputerDto mapComputer(Computer computer) {
		if (computer == null)
			return null;
		return new ComputerDto(computer.getId(), computer.getName(),
				(computer.getIntroduced() != null) ? computer.getIntroduced().toString() : null,
				(computer.getDiscontinued() != null) ? computer.getDiscontinued().toString() : null,
				(computer.getCompany() != null) ? computer.getCompany().getName() : null,
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


}
