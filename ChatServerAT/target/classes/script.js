console.log("Hello world!");

function logIn() {
    var username = prompt("Please enter your username.");
    request({url: "/login_user?username="+username, verb: "GET"})
            .then(username => {
                console.log(username);
                document.getElementById("displayLogIn").value = "Logged in as " + username;
            })
            .catch(error => {
                console.log("error: " + error);
            });
}


function sendMsg() {
    var a = document.getElementById("msgBox").value;
    request({url: "/send?msg="+a, verb: "PUT"})
            .then(a => {
                console.log(a);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function getNew() {
    request({url: "/get", verb: "GET"})
            .then(data => {
                console.log(data);
                document.getElementById("result").value += data;
            })
            .catch(error => {
                console.log("error: " + error);
            });

}




