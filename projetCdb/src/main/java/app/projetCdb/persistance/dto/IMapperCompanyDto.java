package app.projetCdb.persistance.dto;

import java.util.List;
import java.util.Optional;

import app.projetCdb.models.Company;

public interface IMapperCompanyDto {
	public Company mapDto(CompanyDto dto);
	public CompanyDto mapCompany(Company company);
	public List<Company> mapListCompanyDto(List<CompanyDto> listDto);
	public List<CompanyDto> mapListCompany(List<Company> companies);
}
