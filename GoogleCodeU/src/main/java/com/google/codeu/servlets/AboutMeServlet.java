package com.google.codeu.servlets;

import java.io.IOException;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;

@WebServlet("/about")
public class AboutMeServlet extends HttpServlet{ 	
	
	private Data datastore;
	@Override
	public void init()
	{
		datastore = new Datastore();
	}
	
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response)

throws IOException {
		response.setContentType("text/html");
		String user = request.getParameter("user");
		
		if(user== null|| user.contentEquals("")) {
			//Request invalid, return empty response
			return;
		}
		
		String aboutMe = "This is " + user + " 's about me.";
		
		response.getOutputStream().println(aboutMe);
		
		
	}

	@Override
	public void doPost(HttpServletREquest request,HttpServletResponse response)
	throws IOexception {
		UserServixe userService = UserServiceFactory.getUserService();
		if(!userService.isUserLoggedIn()) {
			response.sendRedirect("/index.html");
			return;
		}
	String userEmail = userService.getCurrentUser().getEmail();
	System.out.println("Savinf about me for " + userEmail);
	
	response.sendRedirect("/user-page.html?user= +userEmail");
	
	}
	
	
}



