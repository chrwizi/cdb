package app.projetCdb.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.projetCdb.models.Computer;
import app.projetCdb.persistance.dto.ComputerDto;
import app.projetCdb.persistance.dto.IMapperComputerDto;
import app.projetCdb.persistance.dto.MapperComputer;
import app.projetCdb.services.IComputerService;

//@WebServlet(name = "cdb", urlPatterns = "/")
@Controller
@RequestMapping("/")
public class Homeservlet {

	@Autowired
	private IComputerService computerService;
	
	private IMapperComputerDto mapper = new MapperComputer();
	
	private boolean sortTable = false;
	private boolean ascSort = false;

	@GetMapping("")
	public String doGet(Model model) {
		List<Computer> computers = new ArrayList<Computer>();

		try {
			if (sortTable) {
				computers = computerService.getPageSortedByName(1, true);

			} else {
				computers = computerService.getPage(1);
				model.addAttribute("sortPage", false);
			}
			ArrayList<ComputerDto> computersDto = (ArrayList<ComputerDto>) mapper.mapListComputer(computers);

			model.addAttribute("numPage", 1);
			model.addAttribute("nbPages", computerService.getNbPages());
			model.addAttribute("computers", computersDto);
			model.addAttribute("nbComputers", computersDto.size());

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "dashboard";
	}

	
	
	@RequestMapping("sort")
	public String sort(Model model) {

		return "dashboard";
	}
	
 
	@PostMapping("search")
	public String searchComputer(@RequestParam(name = "research", required = true) String research, Model model) {
		final String view="dashboard";
		List<Computer> computers = new ArrayList<Computer>();

		try {
//			computers = computerService.getPage((selectedPage == null ? 1 : Integer.parseInt(selectedPage)), research);
			computers = computerService.getPage(1, research);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ArrayList<ComputerDto> computersDto = (ArrayList<ComputerDto>) mapper.mapListComputer(computers);
		model.addAttribute("nbPages", computerService.getNbPages());
		model.addAttribute("computers", computersDto);
		model.addAttribute("nbComputers", computersDto.size());
		return view;
	}


}
