package com.google.codeu.data;

import com.google.appengine.repackaged.com.google.gson.JsonObject;

public class User {

  private String email;
  private String aboutMe;
  private int year;
  private String username;

  public User(String email, String aboutMe) {
    this.email = email;
    this.aboutMe = aboutMe;
  }

  public String getEmail(){
    return email;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public int getYear() {
    return year;
  }

  public String getUsername() {
    return username;
  }

  public JsonObject getUserJson() {
    JsonObject json = new JsonObject();
    json.addProperty("email", email);
    json.addProperty("aboutme", aboutMe);
    json.addProperty("year", year);
    json.addProperty("username", username);
    return json;
  } 
}