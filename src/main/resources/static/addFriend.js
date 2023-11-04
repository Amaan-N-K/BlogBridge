document.addEventListener("DOMContentLoaded", function() {

    const addFriendButton = document.querySelector('.add-friends button');
    const friendInput = document.querySelector('.add-friends input');

    addFriendButton.addEventListener('click', function() {
        const friendUsername = friendInput.value.trim();
        if (friendUsername) {
            addFriend(friendUsername);
        } else {
            alert("Please enter a username.");
        }
    });
});

function addFriend(friendUsername) {
    const currentUsername = sessionStorage.getItem('username');
    fetch(`/friends/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: currentUsername, friendUsername: friendUsername })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data.success) {
                alert("Friend request sent successfully!");
            } else {
                alert("Either no user, user already a Friend, already sent request to user");
            }
        })
        .catch(error => console.error("Error adding friend:", error));
}
