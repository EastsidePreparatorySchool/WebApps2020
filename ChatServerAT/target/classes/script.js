console.log("Hello world!");

logIn();

function logIn() {
    var username = prompt("Please enter your username.");
    request({url: "/login_user?username="+username, verb: "GET"})
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
                let messages = JSON.parse(data);
                console.log("this is messages:" + messages);
                let output = "";
                for (var i = 0; i < messages.length; i++) {
                    let s = messages[i];
                    
                    console.log(s);
                    output = output + s + "\n";
                }
               // console.log(data);
                document.getElementById("result").value = output;
                
                 //var msgData = JSON.parse(data);
                 //msgData.forEach(msg => {
                 //document.getElementById("result").value += msg["userName"] + ": " + msg["messageString"] + "\n";});
            })
            .catch(error => {
                console.log("error: " + error);
            });

}

setInterval(getNew, 300);

var x = document.getElementById("msgBox");
//taken from w3schools
x.addEventListener("keyup", function(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    sendMsg();
    x.value="";
  }
});

