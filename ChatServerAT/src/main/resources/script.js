console.log("Hello world!");

function logIn() {
    var username = prompt("Please enter your username.");
    request({url: "/login_user?username="+username, verb: "GET"})
            .then(username => {
                console.log(username);
                document.getElementById("displayLogIn").innerHTML = "Logged in as " + username;
                console.log(document.getElementById("displayLogIn").value);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

logIn();

function sendMsg() {
    var a = document.getElementById("msgBox").value;
    request({url: "/send?msg="+a, verb: "PUT"})
            .then(data => {
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
                
                var x = msgs;
          //      x.join("\n");
                document.getElementById("result").value = x;
            })
            .catch(error => {
                console.log("error: " + error);
            });

}

// setInterval(getNew, 500);





