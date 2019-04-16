package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
/**
 * Handles fetching and saving user data.
 */
@WebServlet("/user-info")
public class UserInfoServlet extends HttpServlet {

  private Datastore datastore;

 @Override
 public void init() {
  datastore = new Datastore();
 }
 
 /**
  * Responds with the "about me" section for a particular user.
  */
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

  response.setContentType("json/application");
  
  String user = request.getParameter("user");
  
  if(user == null || user.equals("")) {
   // Request is invalid, return empty response
   return;
  }
  
  User userData = datastore.getUser(user);

  if(userData == null) {
    return;
  }
  
  JsonObject userJson = userData.getUserJson();
  
  response.getOutputStream().println(userJson.toString());
 }
 
 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

  UserService userService = UserServiceFactory.getUserService();  
  if (!userService.isUserLoggedIn()) {
   response.sendRedirect("/index.html");
   return;
  }
  
  String userEmail = userService.getCurrentUser().getEmail();
  String aboutMe = Jsoup.clean(request.getParameter("about-me"), Whitelist.none());
  //String aboutMe = request.getParameter("about-me");
  User user = new User(userEmail, aboutMe);
  datastore.storeUser(user);
  //System.out.println("Saving about me for " + userEmail);
  // TODO: save the data
  
  response.sendRedirect("/user-page.html?user=" + userEmail);
 }
}