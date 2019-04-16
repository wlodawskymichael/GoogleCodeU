package com.google.codeu.data;

public class User {

  private String email;
  private String aboutMe;
  private String age;
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

  public String getAge() {
    return age;
  }

  public int getYear() {
    return year;
  }

  public String getUsername() {
    return username;
  }
}