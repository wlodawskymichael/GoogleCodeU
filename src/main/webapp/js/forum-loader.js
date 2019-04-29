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
      console.log(thread.threadId)
      const threadDiv = buildThreadDiv(thread)
      var form = document.createElement('form')
      form.setAttribute('method', 'POST')
      form.setAttribute('action', '/posts')
      form.setAttribute('id', 'post-form')

      var text = document.createElement('textarea')
      text.setAttribute('name', 'text')
      text.setAttribute('id', 'text-input')

      var id = document.createElement('input')
      id.setAttribute('name', 'threadId')
      id.setAttribute('value', thread.threadId)
      id.setAttribute('class', 'hidden')

      var input = document.createElement('input')
      input.type = 'submit'
      input.value = 'Reply'

      form.appendChild(text)
      form.appendChild(id)
      form.appendChild(input)
      threadDiv.appendChild(form)
      fetch('/posts').then((res) => {
        return res.json()
      }).then((posts) => {
        posts.forEach((post) => {
          if (post.threadId === thread.threadId) {
            const postDiv = buildPostDiv(post)
            threadDiv.appendChild(postDiv)
          }
        })
      })
      threadContainer.appendChild(threadDiv)
    })
  })
}

/** Build HTML display for threads in the forum */
function buildPostDiv (post) {
  const headerDiv = document.createElement('div')
  headerDiv.classList.add('post-header')
  headerDiv.appendChild(document.createTextNode(post.user))

  const bodyDiv = document.createElement('div')
  bodyDiv.classList.add('post-body')
  bodyDiv.innerHTML = post.text

  const postDiv = document.createElement('div')
  postDiv.classList.add('post-div')
  postDiv.appendChild(headerDiv)
  postDiv.appendChild(bodyDiv)

  return postDiv
}

/** Build HTML display for threads in the forum */
function buildThreadDiv (thread) {
  // console.log(thread.getTopic())
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

  // Thread division
  const threadDiv = document.createElement('div')
  threadDiv.classList.add('thread-div')
  threadDiv.appendChild(headerDiv)

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
