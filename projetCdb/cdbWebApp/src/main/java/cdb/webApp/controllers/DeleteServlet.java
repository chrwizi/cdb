package cdb.webApp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import cdb.service.IComputerService;



@Controller
@RequestMapping("/delete")
//@PreAuthorize("hasAnyRole('ADMIN')")
public class DeleteServlet{
	private  IComputerService computerService ;
	
	public DeleteServlet(IComputerService computerService) {
		this.computerService = computerService;
	}

	@PostMapping
	protected RedirectView doPost(@RequestParam(name="selection",required=true)String selection)  {
		String[] strSelectedIdTab=selection.split(",");
		if (strSelectedIdTab != null) {
			long computersIdTab[] = convertToLong(strSelectedIdTab);
			computerService.delete(computersIdTab);
		}
		return new RedirectView("/projetCdb");
	}
	

	private static long[] convertToLong(String[] strId) {
		long[] tab=null;
		if(strId!=null) {
			tab=new long[strId.length];
			for(int i=0;i<tab.length;i++) {
				tab[i]=Long.valueOf(strId[i]);
			}
		}
		return tab;
	}
	
}
