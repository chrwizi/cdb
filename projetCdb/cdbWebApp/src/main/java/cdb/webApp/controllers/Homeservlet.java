package cdb.webApp.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cdb.binding.ComputerDto;
import cdb.binding.IMapperComputerDto;
import cdb.binding.MapperComputer;
import cdb.core.models.Computer;
import cdb.service.IComputerService;

@RestController
@RequestMapping("/")
public class Homeservlet {

	@Autowired
	private IComputerService computerService;

	private IMapperComputerDto mapper = new MapperComputer();
	private boolean sortTable = false;
	//private boolean ascSort = false;

	@GetMapping("")
	@ResponseBody
	//@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ComputerDto> home(Model model) {
		List<ComputerDto> computersDto=new ArrayList<ComputerDto>();
		boolean sortPage=false;
		try {
			List<Computer> computers = new ArrayList<Computer>();
			if (sortTable) {
				computers = computerService.getPageSortedByName(1, true);
			} else {
				computers = computerService.getPage(1);
				model.addAttribute("sortPage", false);
			}
			computersDto = (ArrayList<ComputerDto>) mapper.mapListComputer(computers);
			model.addAttribute("numPage", 1);
			model.addAttribute("nbPages", computerService.getNbPages());
			model.addAttribute("computers", computersDto);
			model.addAttribute("nbComputers", computersDto.size());
			model.addAttribute("sortPage", sortPage);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computersDto;
		//return "dashboard";
	}

	@RequestMapping("/sort")
	public String sort(@RequestParam(name = "asc", required = true) String asc,
			@RequestParam(name = "selctedPage", required = true) String selectedPage, Model model) {
		List<Computer> computers = new ArrayList<Computer>();
		boolean ascSort = Boolean.parseBoolean(asc);
		int page = Integer.parseInt(selectedPage);
		try {
			computers = computerService.getPageSortedByName(page, ascSort);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<ComputerDto> computersDto = (ArrayList<ComputerDto>) mapper.mapListComputer(computers);

		boolean sortPage = true;
		model.addAttribute("computers", computersDto);
		model.addAttribute("sortPage", sortPage);
		model.addAttribute("nbPages", computerService.getNbPages());
		return "dashboard";
	}

	@PostMapping("search")
	//public List<ComputerDto>searchComputer(@RequestParam(name = "research", required = true) String research, Model model) {
	public String searchComputer(@RequestParam(name = "research", required = true) String research, Model model) {
		
		List<ComputerDto> computersDto = new ArrayList<ComputerDto>();
		List<Computer> computers = new ArrayList<Computer>();
		try {
			
			computers = computerService.getPage(1, research);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		computersDto = (ArrayList<ComputerDto>) mapper.mapListComputer(computers);
		
		model.addAttribute("nbPages", computerService.getNbPages());
		model.addAttribute("computers", computersDto);
		model.addAttribute("nbComputers", computersDto.size());
		
		//return view;
		return "research";
	}

}
