package com.google.codeu.servlets;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import com.google.gson.Gson;
import org.jsoup.safety.Whitelist;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Post;
import com.google.codeu.data.Thread;
import com.google.codeu.data.User;

@WebServlet("/posts")
public class PostServlet extends HttpServlet {

    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String threadId = Jsoup.clean(request.getParameter("threadId"), Whitelist.none());

        List<Post> allPosts = datastore.getPostsFromThread(threadId);
        Gson gson = new Gson();
        String json = gson.toJson(allPosts);

        response.getOutputStream().println(json);
    }

    @Override 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();

        // Redirecting user if not logged in
        if (!userService.isUserLoggedIn()) {
            response.sendRedirect("/index.html");
            return;
        }

        String userEmail = userService.getCurrentUser().getEmail();
        String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());
        String threadIdString = Jsoup.clean(request.getParameter("thread"), Whitelist.none());
        UUID threadId = UUID.fromString(threadIdString);
        
        Post post = new Post(threadId, userEmail, text);
        datastore.storePost(post);
        response.sendRedirect("/posts");
    }
}