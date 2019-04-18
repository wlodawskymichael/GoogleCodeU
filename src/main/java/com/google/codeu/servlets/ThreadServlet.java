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

@WebServlet("/threads")
public class ThreadServlet extends HttpServlet {

    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        List<Thread> allThreads = datastore.getThreads();
        Gson gson = new Gson();
        String json = gson.toJson(allThreads);

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
        String topic = Jsoup.clean(request.getParameter("topic"), Whitelist.none());
        String title = Jsoup.clean(request.getParameter("title"), Whitelist.none());
        
        Thread thread = new Thread(null, title, topic);
        Post firstPost = new Post(thread.getThreadId(), userEmail, text);
        thread.setFirstPostId(firstPost.getPostId());

        datastore.storeThread(thread);
        datastore.storePost(firstPost);
        response.sendRedirect("/threads");
    }
}