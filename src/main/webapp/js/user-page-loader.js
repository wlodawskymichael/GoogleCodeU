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

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');
var searchParams = {};


// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterUsername;
  document.title = parameterUsername + ' - User Page';
}

/**
 * TODO: Add logic to show year, username, and about me if entered. The user should always have 
 * editing options. However, the infromation should be highlighted and require filling out before
 * the user can post to a thread.
 */

 /**
  * Helper function to make it easier to modularize html styling for 
  * alerting the user they need to add information.
  */
  function alertUserToEdit(elementId, message, param) {
    const response = prompt(message, "");
    if (response == "" || response == null) {
      alertUserToEdit(elementId, message);
    }
    // Make sure we have a valid year
    if (elementId == 'year-block') {
      const num = Number(response);
      if (!(num >= 1 && num <= 5)) {
        alertUserToEdit(elementId, "Year must be a valid Integer between 1 and 5!");
      }
    }
    document.getElementById(elementId).innerText = response;
    searchParams[param] = response;
    return true;
  }

 /**
  * Get the user information and inform the user if they need to make 
  * changes and/or add information.
  */
  function getUserInfo() {
    var needToWrite = false;
    fetch('/user-info').then((response) => {
      return response.json();
    }).then((userData) => {
      if (userData.username == null || userData.username == "") {
        needToWrite = alertUserToEdit('username-block', 'Username field is required!', 'username');
      } else {
        document.getElementById('username-block').innerText = userData.username;
      }
      if (userData.year == null || userData.year == 0) {
        needToWrite = alertUserToEdit('year-block', 'Year field is required!', 'year');
      } else {
        document.getElementById('year-block').innerText = userData.year;
      }
      if (userData.aboutMe == null || userData.aboutMe == "") {
        needToWrite = alertUserToEdit('about-me-block', 'About Me field is required!', 'aboutMe');
      } else {
        document.getElementById('about-me-block').innerText = userData.aboutMe;
      }
      if (needToWrite) {
        var url = '/user-info';
        const info = {
          method: 'POST',
          body: JSON.stringify(searchParams),
          headers: {
            'Content-Type': 'application/json'
          }
        }
        return fetch(url, info);
      }
    }).then((response) => {
      console.log(response.json());
    })
  }

/**
 * Shows the message form if the user is logged in.
 */
function showMessageForm() {
  fetch('/login-status')
      .then((response) => {
        return response.json();
      })
      .then((loginStatus) => {
        if (loginStatus.isLoggedIn) {
          const messageForm = document.getElementById('message-form');
          messageForm.action = '/messages?recipient=' + parameterUsername;
          messageForm.classList.remove('hidden');
        }
      });
}

/** Fetches messages and add them to the page. */
function fetchMessages() {
  const url = '/messages?user=' + parameterUsername;
  fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((messages) => {
        const messagesContainer = document.getElementById('message-container');
        if (messages.length == 0) {
          messagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
        } else {
          messagesContainer.innerHTML = '';
        }
        messages.forEach((message) => {
          const messageDiv = buildMessageDiv(message);
          messagesContainer.appendChild(messageDiv);
        });
      });
}

/**
 * About Me
 * */
/*function fetchAboutMe(){
  const url = '/about?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.text();
  }).then((aboutMe) => {
    const aboutMeContainer = document.getElementById('about-me-container');
    if(aboutMe == ''){
      aboutMe = 'This user has not entered any information yet.';
    }
    aboutMeContainer.innerHTML = aboutMe;
  });
}*/


/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildMessageDiv(message) {
  const headerDiv = document.createElement('div');
  headerDiv.classList.add('message-header');
  headerDiv.appendChild(document.createTextNode(
    message.user + ' - ' +
    new Date(message.timestamp) +
    ' [' + message.sentimentScore + ']'));

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-body');
  bodyDiv.innerHTML = message.text;

  const messageDiv = document.createElement('div');
  messageDiv.classList.add('message-div');
  messageDiv.appendChild(headerDiv);
  messageDiv.appendChild(bodyDiv);

  if (message.sentimentScore > 0.0){
    messageDiv.style.backgroundColor = "#fce158";
  } else if (message.sentimentScore == 0.0){
    messageDiv.style.backgroundColor = "#c5f990";
  } else {
    messageDiv.style.backgroundColor = "#a9f9ef";
  }
  return messageDiv;
}

/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  showMessageForm();
  fetchMessages();
  //fetchAboutMe();
  getUserInfo();
}
