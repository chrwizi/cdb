package app.projetCdb.controllers;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import app.projetCdb.tmpServices.IAsService;

@Component("mycomp")
@WebServlet(name = "spring", urlPatterns = "/testSpring")
public class TestSpringServlet extends HttpServlet {
	@Autowired 
	private IAsService service;

	private static final long serialVersionUID = 1L;
	private static final String VIEW= "/WEB-INF/Test.jsp";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String Hello=service.useServiceHello();
		req.setAttribute("message", Hello);
		this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	

}
