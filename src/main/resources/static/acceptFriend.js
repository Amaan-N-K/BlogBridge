function acceptFriendRequest(friendUsername) {
    let loggedInUsername = sessionStorage.getItem('username'); // Retrieve the logged-in username from sessionStorage

    fetch(`/friends/accept`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: loggedInUsername, friendUsername: friendUsername })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Refresh the page to update the dashboard
                location.reload();
            } else {
                console.error("Failed to accept friend request.");
            }
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error.message);
        });
}
