package app.projetCdb.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;
import app.projetCdb.persistance.CompanyDao;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.DbAccess;
import app.projetCdb.persistance.dto.CompanyDto;
import app.projetCdb.persistance.dto.ComputerDto;
import app.projetCdb.persistance.dto.IMapperCompanyDto;
import app.projetCdb.persistance.dto.IMapperComputerDto;
import app.projetCdb.persistance.dto.MapperCompanyDto;
import app.projetCdb.persistance.dto.MapperComputer;
import app.projetCdb.services.CompanyService;
import app.projetCdb.services.ComputerServices;
import app.projetCdb.services.ICompanyServices;
import app.projetCdb.services.IComputerService;


@WebServlet(name="edit",urlPatterns="/editComputer")
public class EditComputerSevlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	//views
	private static final String REDIRECT_VIEW="/WEB-INF/editComputer.jsp";
	private static final String REDIRECT_COMPUTER_NOT_FOUND="/WEB-INF/500.jsp";
	//database access
	private ComputerDao computerDao=new ComputerDao(DbAccess.getInstance());
	private CompanyDao companyDao=new CompanyDao(DbAccess.getInstance());
	//services
	private IComputerService computerService = new ComputerServices(computerDao);
	private ICompanyServices companyService=new CompanyService(companyDao);
	//mappers
	private IMapperComputerDto mapper=new MapperComputer();
	private IMapperCompanyDto mapperCompany=new MapperCompanyDto();

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long idComputer=Long.valueOf(req.getParameter("idComputer"));
		//erreur sur id � g�rer 

		Optional<Computer> optionalComputer=computerService.finById(idComputer);
		if(!optionalComputer.isPresent()) {
			//there is no computer corresponding with Id
			String errorMessage="Aucun ordinateur trouv�";
			req.setAttribute("errorMessage", errorMessage);
			this.getServletContext().getRequestDispatcher(REDIRECT_COMPUTER_NOT_FOUND).forward(req, resp);
		}
		else {
			Optional<List<Company>> optionalCompanies=companyService.getAll();  
			ComputerDto computerDto=mapper.mapComputer(optionalComputer.get());		
			Optional<List<CompanyDto>>companiesDto=mapperCompany.mapListCompany(optionalCompanies.get());
			
			req.setAttribute("companies", companiesDto.get());
			req.setAttribute("computer", computerDto);
			
			this.getServletContext().getRequestDispatcher(REDIRECT_VIEW).forward(req, resp);
		}
	}



}
