package app.projetCdb.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import app.projetCdb.services.ComputerService;
import app.projetCdb.services.IComputerService;

@WebServlet(name = "delete", urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {
	// services
	@Autowired
	private IComputerService computerService ;
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String view =req.getContextPath()+"/dashboard";
		if (req.getParameter("selection") != null) {
			long computersIdTab[] = convertToLong(req.getParameter("selection").split(","));
			computerService.delete(computersIdTab);
		}
		resp.sendRedirect(view);
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
