package cdb.binding;

import java.util.List;
import java.util.Optional;

import cdb.core.models.Company;

public interface IMapperCompanyDto {
	public Company mapDto(CompanyDto dto);
	public CompanyDto mapCompany(Company company);
	public List<Company> mapListCompanyDto(List<CompanyDto> listDto);
	public List<CompanyDto> mapListCompany(List<Company> companies);
}
