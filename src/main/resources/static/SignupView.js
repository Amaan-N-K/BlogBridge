document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();
    console.log("Form submitted");
    let formData = new FormData(e.target);

    let user = {};
    formData.forEach((value, key) => user[key] = value);
    console.log("Converted user data:", user);

    fetch('http://localhost:8080/signup', {
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
            if (data.userExists) {
                console.log("Username already exists");
                alert("Username already exists!");
                // Handle UI update for username exists
            } else if (data.passwordsMismatch) {
                console.log("Passwords do not match");
                alert("Passwords do not match!");
                // Handle UI update for password mismatch
            } else {
                console.log("Data sent successfully");
                window.location.href = "/login.html";
            }
        })
        .catch(err => {
            console.log("Error sending data", err);
            alert("An error occurred. Please try again.");
        });
});
