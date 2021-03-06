package cdb.binding;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cdb.core.models.Company;

@Service("mapperCompanyDto")
public class MapperCompanyDto implements IMapperCompanyDto {

	@Override
	public Company mapDto(CompanyDto dto) {
		return (dto != null) ? new Company(dto.getId(), dto.getName()) : null;
	}

	@Override
	public CompanyDto mapCompany(Company company) {
		return (company != null) ? new CompanyDto(company.getId(), company.getName()) : null;
	}

	@Override
	public List<Company> mapListCompanyDto(List<CompanyDto> listDto) {
		List<Company> companies = new ArrayList<>();
		if (listDto != null) {
			for (CompanyDto companyDto : listDto) {
				companies.add(mapDto(companyDto));
			}
		}
		return companies;
	}
 
	@Override
	public List<CompanyDto> mapListCompany(List<Company> companies) {
		List<CompanyDto> companiesDto = new ArrayList<>();
		if (companies != null) {
			for (Company company : companies) {
				companiesDto.add(mapCompany(company));
			}
		}
		return companiesDto;
	}

}
