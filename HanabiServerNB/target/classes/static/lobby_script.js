logIn();
setInterval(getNew, 300);

// optional chat feature (Aybala)

function logIn() {
    request({url: "/login_user?username=" + username, verb: "GET"})
            .then(username => {
                console.log(username);
                document.getElementById("displayLogIn").innerHTML = "Logged in as " + username + ".";
                console.log(document.getElementById("displayLogIn").value);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

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
                var messages = JSON.parse(JSON.parse(data)); 
                var msgOutput = "";
                for(var i = 0; i < messages.length; i++) {
                    var msg = messages[i]; 
                    msgOutput += msg.username + ": " + msg.msg + "\n";  // formatting output properly       
                }       
                
                document.getElementById("result").value = msgOutput; // displaying formatted output in text area
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

// sends message by just pressing enter
var x = document.getElementById("msgBox");
//taken from w3schools
x.addEventListener("keyup", function (event) {
    if (event.keyCode === 13) {
        event.preventDefault();
        sendMsg();
        x.value = "";
    }
});
