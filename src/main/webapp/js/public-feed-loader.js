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

/** Fetch messages and populate the public feed */
function fetchMessages() {
    const url = '/feed';
    fetch(url).then((res) => {
        return res.json();
    }).then((messages) => {
        const messageContainer = document.getElementById('message-container');
        if (messages.length == 0) {
            messageContainer.innerHTML = '<p>There are no posts yet!</p>'
        } else {
            messageContainer.innerHTML = '';
        }
        messages.forEach((message) => {
            const messageDiv = buildMessageDiv(message);
            messageContainer.appendChild(messageDiv);
        });
    });
}

/** Build HTML display for messages on the public feed */
function buildMessageDiv(message) {
    //Username division
    const usernameDiv = document.createElement('div');
    usernameDiv.classList.add("left-align");
    usernameDiv.appendChild(document.createTextNode(message.user));

    //Date/Time division
    const timeDiv = document.createElement('div');
    timeDiv.classList.add("right-align");
    timeDiv.appendChild(document.createTextNode(new Date(message.timestamp)));

    //Header division
    const headerDiv = document.createElement('div');
    headerDiv.classList.add('message-header');
    headerDiv.appendChild(usernameDiv);
    headerDiv.appendChild(timeDiv);

    //Body division
    const bodyDiv = document.createElement('div');
    bodyDiv.classList.add('message-body');
    bodyDiv.appendChild(document.createTextNode(message.text));

    //Message division
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message-div');
    messageDiv.appendChild(headerDiv);
    messageDiv.appendChild(bodyDiv);

    return messageDiv;
}

/** Fetch the messages and populate the UI with the public feed */
function buildUI() {
    fetchMessages();
}