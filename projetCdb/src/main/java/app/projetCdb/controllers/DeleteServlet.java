package app.projetCdb.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.projetCdb.services.ComputerServices;
import app.projetCdb.services.IComputerService;

@WebServlet(name = "delete", urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {
	// services
	private IComputerService computerService = new ComputerServices();

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String view =req.getContextPath()+"/dashboard";
		if (req.getParameter("selection") != null) {
			String computersToDelete[] = req.getParameter("selection").split(",");
			long idTab[] = new long[computersToDelete.length];
			for (int i = 0; i < idTab.length; i++) {
				idTab[i] = Long.valueOf(computersToDelete[i]);
			}
			computerService.delete(idTab);
		}
		resp.sendRedirect(view);
	}

}
