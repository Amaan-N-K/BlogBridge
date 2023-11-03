document.addEventListener("DOMContentLoaded", function() {
    let username = sessionStorage.getItem('username'); // Retrieve username from sessionStorage

    // Fetch data from the backend
    fetch(`/dashboard?username=${username}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.username) {
                document.getElementById("usernameDisplay").textContent = "Username: " + data.username;
            }

            if (typeof data.friendCount !== 'undefined') {
                document.querySelector(".header-info span:nth-child(2)").textContent = "Friends: " + data.friendCount;
            }

            const friendsList = document.querySelector('.friends');
            if (Array.isArray(data.friends) && data.friends.length) {
                friendsList.innerHTML += data.friends.map(friend => `<p>${friend}</p>`).join("");
            } else {
                friendsList.innerHTML += "<p>No friends yet.</p>";
            }

            const invitationList = document.querySelector('.invitations');

            if (Array.isArray(data.friendRequests) && data.friendRequests.length) {
                const validRequests = data.friendRequests.filter(request => request !== 'none');
                if (validRequests.length) {
                    invitationList.innerHTML += validRequests.map(request =>
                        `<p>${request} <button onclick="acceptFriendRequest('${request}')">Accept</button></p>`
                    ).join("");
                } else {
                    invitationList.innerHTML += "<p>No friend requests yet.</p>";
                }
            } else {
                invitationList.innerHTML += "<p>No friend requests yet.</p>";
            }

            const postsContainer = document.getElementById('postsContainer');

            if (Array.isArray(data.posts) && data.posts.length) {
                postsContainer.innerHTML = data.posts.map(post => `
            <div class="post">
                <div class="post-header">
                    <span>${post.title}</span>
                    <span>by: ${post.username}</span>
                </div>
                <div class="post-body">
                    ${post.body}
                </div>
            </div>
        `).join("");
            } else {
                postsContainer.innerHTML = "<p>No tweets yet. Start sharing!</p>";
            }


            // Hide loading and show the container
            document.getElementById("container").style.display = "flex";
            document.getElementById("loading").style.display = "none";
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error.message);
            document.getElementById("loading").textContent = "Failed to load data. Please refresh.";
        });
});

document.addEventListener("DOMContentLoaded", function() {
    // Add event listener for the Create button
    document.getElementById("createPostButton").addEventListener("click", postActions);

    // Add event listener for the Cancel button
    document.getElementById("cancelPostButton").addEventListener("click", hideModal);
});

function goToCreatePostPage() {
    // Show the modal
    document.getElementById("createPostModal").style.display = "flex";
}

