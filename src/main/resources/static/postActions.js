function postActions() {
    const title = document.getElementById("postTitle").value;
    const body = document.getElementById("postBody").value;
    const username = sessionStorage.getItem('username'); // Retrieve username from session storage

    // Handle empty title, body or username
    if (!title.trim() || !body.trim() || !username.trim()) {
        alert("Username, title, and body cannot be empty!");
        return;
    }

    fetch('/createPost', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username: username,
            title: title,
            body: body
        })
    })
        .then(response => response.json())
        .then(data => {
            // Handle the response from the backend
            console.log(data);
            hideModal();
            window.location.reload(); // Refresh the page
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function hideModal() {
    document.getElementById("postTitle").value = ""; // Clear title field
    document.getElementById("postBody").value = "";  // Clear body field
    document.getElementById("createPostModal").style.display = "none"; // Hide modal
}
