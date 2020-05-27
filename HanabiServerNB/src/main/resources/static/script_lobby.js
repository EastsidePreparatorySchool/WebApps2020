logIn();

function logIn() {
    request({url: "/login", method: "GET"})
            .then(username => {
                console.log(username);
                document.getElementById("displayusername").innerHTML = "Logged in as " + username + ".";
                console.log(document.getElementById("displayusername").value);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}