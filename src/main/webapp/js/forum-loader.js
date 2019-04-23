/** Fetch threads and populate the forums */
function fetchThreads () {
  const url = '/forums'
  fetch(url).then((res) => {
    return res.json()
  }).then((threads) => {
    const threadContainer = document.getElementById('thread-container')
    if (threads.length === 0) {
      threadContainer.innerHTML = '<p>There are no posts yet!</p>'
    } else {
      threadContainer.innerHTML = ''
    }
    threads.forEach((thread) => {
      const threadDiv = buildThreadDiv(thread)
      threadContainer.appendChild(threadDiv)
    })
  })
}

/** Build HTML display for threads in the forum */
function buildThreadDiv (thread) {
  // Topic division
  const topicDiv = document.createElement('div')
  topicDiv.classList.add('left-align')
  topicDiv.appendChild(document.createTextNode(thread.topic))

  // Title division
  const titleDiv = document.createElement('div')
  titleDiv.classList.add('right-align')
  titleDiv.appendChild(document.createTextNode(thread.title))

  // Header division
  const headerDiv = document.createElement('div')
  headerDiv.classList.add('thread-header')
  headerDiv.appendChild(topicDiv)
  headerDiv.appendChild(titleDiv)

  // Body division
  const bodyDiv = document.createElement('div')
  bodyDiv.classList.add('thread-body')
  bodyDiv.appendChild(document.createTextNode(thread.text))

  // Thread division
  const threadDiv = document.createElement('div')
  threadDiv.classList.add('thread-div')
  threadDiv.appendChild(headerDiv)
  threadDiv.appendChild(bodyDiv)

  return threadDiv
}

/**
 * Shows the message form if the user is logged in.
 */
function showThreadForm () {
fetch('/login-status')
    .then((response) => {
      return response.json()
    })
    .then((loginStatus) => {
      if (loginStatus.isLoggedIn) {
        const threadForm = document.getElementById('thread-form')
        threadForm.action = '/forums'
        threadForm.classList.remove('hidden')
      }
    })
}

/** Fetch the threads and populate the UI with them */
function buildUI () {
  showThreadForm()
  fetchThreads()
}
