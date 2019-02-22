package app.projetCdb.persistance.dto;

import java.util.List;

import app.projetCdb.models.Computer;

public interface IMapperComputerDto {
	public List<Computer> mapListDto(List<ComputerDto>listComputers);
	public List<ComputerDto> mapListComputer(List<Computer> listDto);
	public ComputerDto mapComputer(Computer computer);
	public Computer mapDto(ComputerDto dto);
}
