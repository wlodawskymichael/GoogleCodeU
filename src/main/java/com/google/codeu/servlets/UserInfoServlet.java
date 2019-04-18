package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles fetching and saving user data. */
@WebServlet("/user-info")
public class UserInfoServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /** Responds with the "about me" section for a particular user. */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");


    UserService userService = UserServiceFactory.getUserService();
    String userEmail = userService.getCurrentUser().getEmail();

    if (userEmail == null || userEmail.equals("")) {
      // Request is invalid, return empty response
      return;
    }

    User userData = datastore.getUser(userEmail);

    if (userData == null) {
      return;
    }

    JsonObject userJson = userData.getUserJson();
    System.out.println(userJson.toString());
    response.getWriter().println(userJson.toString());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String userEmail = userService.getCurrentUser().getEmail();

    // Process the body of the request for the user's json data
    StringBuffer jsonBuffer = new StringBuffer();
    String line = null;
    // Read in body parameters
    try {
      BufferedReader requestBody = request.getReader();
      while ((line = requestBody.readLine()) != null) {
        jsonBuffer.append(line);

        System.out.println(line);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    // Convert body parameters to json
    JsonObject bodyJson = new JsonParser().parse(jsonBuffer.toString()).getAsJsonObject();
    JsonElement usernameElement = bodyJson.getAsJsonObject().get("username");
    String username = (usernameElement == null) ? "" : usernameElement.getAsString();
    JsonElement aboutMeElement = bodyJson.getAsJsonObject().get("aboutMe");
    String aboutMe = (aboutMeElement == null) ? "" : aboutMeElement.getAsString();
    JsonElement yearElement = bodyJson.getAsJsonObject().get("year");
    int year = (yearElement == null) ? 0 : yearElement.getAsInt();

    User user = new User(userEmail, aboutMe, username, year);
    datastore.storeUser(user);

    response.getWriter().println(jsonBuffer.toString());
  }
}
