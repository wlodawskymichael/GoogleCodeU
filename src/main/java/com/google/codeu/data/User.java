package com.google.codeu.data;

import com.google.gson.JsonObject;

public class User {

  private String email = "";
  private String aboutMe = "";
  private int year = 0;
  private String username = "";

  public User(String email, String aboutMe) {
    this.email = email;
    this.aboutMe = aboutMe;
  }

  public User(String email, String aboutMe, String username, int year) {
    this.email = email;
    this.aboutMe = aboutMe;
    this.username = username;
    this.year = year;
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
    json.addProperty("aboutMe", aboutMe);
    json.addProperty("year", year);
    json.addProperty("username", username);
    return json;
  } 
}