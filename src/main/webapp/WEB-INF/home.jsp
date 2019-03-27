<!--
Copyright 2019 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
<% boolean isUserLoggedIn = (boolean) request.getAttribute("isUserLoggedIn"); %>

<!DOCTYPE html>
<html>
  
  <head>
    <!-- Metadata of the sight -->
    <meta charset="UTF-8">
    <title>CodeU 2019 Starter Project</title>
    <link rel="stylesheet" href="../css/main.css">
  </head>
  <body>
    <header class="animated-header">
      <h2 id="title">Animator's Paradise</h2>
    </header>
    <nav class="navbar">
        <ul id="navigation">
            <li><a class="button" href="/">Home</a></li>
            <% if(isUserLoggedIn) {
                String username = (String) request.getAttribute("username"); %>
                <li><a class="button" href="/feed.html">Feed</a></li>
                <li><a class="button" href="/user-page.html?user=<%= username %>">Your Page</a></li>
                <li><a class="button" href="/logout">Logout</a></li>
                <li><a class="button" href="/stats-page.html">Stats Page</a></li>
            <%} else {%>
                <li><a class="button" href="/login">Login</a></li>
            <%}%>
        </ul>
    </nav>
    <div class="content">
        <a class="button" href="../about-us.html">About Us!</a>
        <p>Examples of work we promote:</p>
        <div class="img-container">
          <div class="column">
            <img src="../attributes/astronaut-animation.gif">
          </div>
          <div class="column">
            <img src="../attributes/rhino-animation.gif">
          </div>
          <div class="column">
            <img src="../attributes/space-oscar-animation.gif">
          </div>
        </div>

        <h1>CodeU Starter Project</h1>
        <p>This is the CodeU starter project. Click the links above to login and visit your page.
        You can post messages on your page, and you can visit other user pages if you have
        their URL.</p>
        <p>This is your code now! Feel free to make changes, and don't be afraid to get creative!
        You could start by modifying this page to tell the world more about your team.</p>
    </div>
  </body>
</html>