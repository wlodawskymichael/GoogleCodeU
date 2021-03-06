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

package com.google.codeu.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Provides access to the data stored in Datastore. */
public class Datastore {

  private DatastoreService datastore;
  private final String defaultMessageUser = null;
  private static final String USER = "user";
  private static final String RECIPIENT = "recipient";

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /** Stores the Message in Datastore. */
  public void storeMessage(Message message) {
    Entity messageEntity = new Entity("Message", message.getId().toString());
    messageEntity.setProperty(USER, message.getUser());
    messageEntity.setProperty("text", message.getText());
    messageEntity.setProperty("timestamp", message.getTimestamp());
    messageEntity.setProperty(RECIPIENT, message.getRecipient());
    messageEntity.setProperty("sentimentScore", message.getSentimentScore());

    datastore.put(messageEntity);
  }

  /** Stores a Post in the Datastore. */
  public void storePost(Post post) {
    Entity postEntity = new Entity("Post", post.getPostId().toString());
    postEntity.setProperty("threadId", post.getThreadId().toString());
    postEntity.setProperty("user", post.getUser());
    postEntity.setProperty("text", post.getText());
    postEntity.setProperty("timestamp", post.getTimestamp());

    datastore.put(postEntity);
  }

  /**
   * Gets all posts in a given thread
   *
   * @return a list of posts in a thread. List is sorted by time descending.
   */
  public List<Post> getPostsFromThread(String threadId) {
    List<Post> posts = new ArrayList<>();
    Query query = new Query("Post")
                      .setFilter(new Query.FilterPredicate("threadId", FilterOperator.EQUAL, threadId))
                      .addSort("timestamp", SortDirection.DESCENDING);

    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      try {
        String postIdString = entity.getKey().getName();
        UUID postId = UUID.fromString(postIdString);
        String threadIdString = (String) entity.getProperty("threadId");
        UUID threadid = UUID.fromString(threadIdString);
        String user = (String) entity.getProperty("user");
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");

        Post post = new Post(postId, threadid, user, text, timestamp);
        posts.add(post);
      } catch (Exception e) {
        System.err.println("Error reading post.");
      }
    }
    return posts;
  }

  /**
   * Gets all posts
   *
   * @return a list of posts. List is sorted by time ascending.
   */
  public List<Post> getPosts() {
    List<Post> posts = new ArrayList<>();
    Query query = new Query("Post")
                      .addSort("timestamp", SortDirection.ASCENDING);

    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      try {
        String postIdString = entity.getKey().getName();
        UUID postId = UUID.fromString(postIdString);
        String threadIdString = (String) entity.getProperty("threadId");
        UUID threadid = UUID.fromString(threadIdString);
        String user = (String) entity.getProperty("user");
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");

        Post post = new Post(postId, threadid, user, text, timestamp);
        posts.add(post);
      } catch (Exception e) {
        System.err.println("Error reading post.");
      }
    }
    return posts;
  }

  /** Stores a thread in the Datastore. **/
  public void storeThread(Thread thread) {
    Entity threadEntity = new Entity("Thread", thread.getThreadId().toString());
    threadEntity.setProperty("firstPostId", thread.getFirstPostId().toString());
    threadEntity.setProperty("title", thread.getTitle());
    threadEntity.setProperty("topic", thread.getTopic());
    threadEntity.setProperty("timestamp", thread.getTimestamp());

    datastore.put(threadEntity);
  }

  /**
   * Gets all threads in a given forum, sorted by time descending.
   *
   * @return a list of threads in a forum
   */
  public List<Thread> getThreads() {
    List<Thread> threads = new ArrayList<>();
    Query query = new Query("Thread")
                  .addSort("timestamp", SortDirection.DESCENDING);

    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      try {
        String threadIdString = entity.getKey().getName();
        UUID threadId = UUID.fromString(threadIdString);
        String firstPostIdString = (String) entity.getProperty("firstPostId");
        UUID firstPostId = UUID.fromString(firstPostIdString);

        String title = (String) entity.getProperty("title");
        String topic = (String) entity.getProperty("topic");
        long timestamp = (long) entity.getProperty("timestamp");

        Thread thread = new Thread(threadId, firstPostId, title, topic, timestamp);
        threads.add(thread);
      } catch (Exception e) {
        System.err.println("Error reading thread.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    return threads;
  }

  /**
   * Gets all posts from a given user
   *
   * @return a list of posts from a user. List is sorted by time descending.
   */
  public List<Post> getPostsFromUser(String user) {
    List<Post> posts = new ArrayList<>();
    Query query = new Query("Post")
                      .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
                      .addSort("timestamp", SortDirection.DESCENDING);

    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      try {
        String postIdString = entity.getKey().getName();
        UUID postId = UUID.fromString(postIdString);
        String threadIdString = (String) entity.getProperty("threadId");
        UUID threadid = UUID.fromString(threadIdString);
        String user_email = (String) entity.getProperty("user");
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");

        Post post = new Post(postId, threadid, user_email, text, timestamp);
        posts.add(post);
      } catch (Exception e) {
        System.err.println("Error reading post.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    return posts;
  }

/**
 * Gets all threads in a given forum, sorted by time descending.
 *
 * @return a list of threads in a forum
 */
public List<Thread> getThreadsFromTopic(String topic) {
  List<Thread> threads = new ArrayList<>();
  Query query = new Query("Thread")
                .setFilter(new Query.FilterPredicate("topic", FilterOperator.EQUAL, topic))
                .addSort("timestamp", SortDirection.DESCENDING);

  PreparedQuery results = datastore.prepare(query);
  for (Entity entity : results.asIterable()) {
    try {
      String threadIdString = entity.getKey().getName();
      UUID threadId = UUID.fromString(threadIdString);
      String firstPostIdString = (String) entity.getProperty("firstPostId");
      UUID firstPostId = UUID.fromString(firstPostIdString);

      String title = (String) entity.getProperty("title");
      String topicString = (String) entity.getProperty("topic");
      long timestamp = (long) entity.getProperty("timestamp");

      Thread thread = new Thread(threadId, firstPostId, title, topicString, timestamp);
      threads.add(thread);
    } catch (Exception e) {
      System.err.println("Error reading thread.");
      System.err.println(entity.toString());
      e.printStackTrace();
    }
  }
  return threads;
}

  /**
   * Gets messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */
  public List<Message> getMessages(String name, String queryType) {
    List<Message> messages = new ArrayList<>();
    Query query = (name != null) ? new Query("Message")
                                    .setFilter(new Query.FilterPredicate(queryType, FilterOperator.EQUAL, name))
                                    .addSort("timestamp", SortDirection.DESCENDING) :
                                  new Query("Message")
                                    .addSort("timestamp", SortDirection.DESCENDING);

    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");
        float sentimentScore = entity.getProperty("sentimentScore") == null?
        (float) 0.0 : ((Double) entity.getProperty("sentimentScore")).floatValue();
        Message message = null;

        if (USER.equals(queryType)) {
          String recipient = (String) entity.getProperty(RECIPIENT);

          message = new Message(id, name, text, timestamp, recipient, sentimentScore);
        }
        else if (RECIPIENT.equals(queryType)){
          String user = (String) entity.getProperty(USER);

          message = new Message(id, user, text, timestamp, name, sentimentScore);
        }

        messages.add(message);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return messages;
}

  /**
   * Get messages posted by every user.
   *
   * @return a list of messages posted by all users of the site, or an empty list if
   *    there are no messages
   */
  public List<Message> getAllMessages() {
    return getMessages(defaultMessageUser, USER);
  }

  /**
   * Gets total number of messages posted.
   *
   * @return an integer respresenting the total number of messages posted on the webpage.
   */
  public int getTotalMessageCount() {
    PreparedQuery results = datastore.prepare(new Query("Message"));
    return results.countEntities(FetchOptions.Builder.withLimit(1000));
  }

  /** Stores the User in Datastore. */
  public void storeUser(User user) {
   Entity userEntity = new Entity("User", user.getEmail());
   userEntity.setProperty("email", user.getEmail());
   userEntity.setProperty("aboutMe", user.getAboutMe());
   userEntity.setProperty("username", user.getUsername());
   userEntity.setProperty("year", user.getYear());
   datastore.put(userEntity);
  }

  /**
   * Returns the User owned by the email address, or
   * null if no matching User was found.
   */
  public User getUser(String email) {

     Query userEmailQuery = new Query("User")
       .setFilter(new Query.FilterPredicate("email", FilterOperator.EQUAL, email));
     PreparedQuery results = datastore.prepare(userEmailQuery);
     Entity userEntity = results.asSingleEntity();
     if(userEntity == null) {
      return null;
     }

     String aboutMe = (String) userEntity.getProperty("aboutMe");
     String username = (String) userEntity.getProperty("username");
     Long year = (Long) userEntity.getProperty("year");
     User user = new User(email, aboutMe, username, year.intValue());

     return user;
  }

}
