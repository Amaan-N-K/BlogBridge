document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();
    console.log("Form submitted");
    let formData = new FormData(e.target);

    let user = {};
    formData.forEach((value, key) => user[key] = value);
    console.log("Converted user data:", user);

    fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Server responded with status: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            if (!data.success) {
                console.log("Incorrect username or password");
                alert("Incorrect username or password");
                // Handle UI update for username exists
            } else {
                console.log("Data sent successfully");
                sessionStorage.setItem('username', user.username);  // Store the username
                window.location.href = "/dashboard.html";
            }
        })
        .catch(err => {
            console.log("Error sending data", err);
            alert("An error occurred. Please try again.");
        });
});
