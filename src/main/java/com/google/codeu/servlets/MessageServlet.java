/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import java.util.HashMap;
import java.util.Map;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
   * an empty array if the user is not provided.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");

    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[not logged in]");
      return;
    }

    List<Message> messages = datastore.getMessages(user, "recipient");

    Gson gson = new Gson();
    String json = gson.toJson(messages);

    response.getWriter().println(json);
  }

  /** Stores a new {@link Message}. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String user = userService.getCurrentUser().getEmail();
    String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());
    String recipient = request.getParameter("recipient");

    // Styling text
    text = convertBBCode(text);
    float sentimentScore = this.getSentimentScore(text);


    // Replacing image url to img tag
    String imageUrlRegexExpression = "(https?://\\S+\\.(png|jpg|gif))";
    String htmlImgTagReplacement = "<img src=\"$1\" />";
    System.out.println(text);
    String textWithImageTags = text.replaceAll(imageUrlRegexExpression, htmlImgTagReplacement);
    System.out.println(textWithImageTags);

    Message message = new Message(user, textWithImageTags, recipient, sentimentScore);
    datastore.storeMessage(message);

    response.sendRedirect("/user-page.html?user=" + recipient);
  }

  /**
   * Parses message text and changes BBCode tags on styled text to HTML tags
   */
  public String convertBBCode(String text){
      String temp = text;

      Map<String , String> bbMap = new HashMap<String , String>();
      bbMap.put("\\[b\\](.+?)\\[/b\\]", "<strong>$1</strong>");
      bbMap.put("\\[i\\](.+?)\\[/i\\]", "<em>$1</em>");
      bbMap.put("\\[u\\](.+?)\\[/u\\]", "<u>$1</u>");
      bbMap.put("\\[s\\](.+?)\\[/s\\]", "<del>$1</del>");

      for (Map.Entry entry: bbMap.entrySet()) {
        temp = temp.replaceAll(entry.getKey().toString(), entry.getValue().toString());
      }
      return temp;

  }

  /**
   * Uses Google NLP API to get the sentiment score of a message
   */
  private float getSentimentScore(String text) throws IOException {
      Document message = Document.newBuilder()
          .setContent(text).setType(Type.PLAIN_TEXT).build();

      LanguageServiceClient languageService = LanguageServiceClient.create();
      Sentiment sentiment = languageService.analyzeSentiment(message).getDocumentSentiment();
      languageService.close();

      return sentiment.getScore();
  }

}
