package com.google.codeu.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;

/** Handles fetching all messages for public feed. */
 @WebServlet("/feed")
 public class MessageFeedServlet extends HttpServlet {

    private Datastore datastore;

    public MessageFeedServlet() {
        datastore = new Datastore();
    }

    /**
     * Responds with a JSON response of all Message data for ALL Users
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
        throws IOException {
            res.setContentType("application/json");

            List<Message> allMessages = datastore.getAllMessages();
            Gson gson = new Gson();

            String json = gson.toJson(allMessages);

            res.getOutputStream().println(json);
    }
 }