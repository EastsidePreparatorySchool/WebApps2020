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
                // parsing message (took a while to figure out why the parsing 
                // wasn't working, I was able to solve the problem by doing it 
                // twice but I'm still unsure as to why it didn't just work by 
                // doing it once the first time)
                // here's what helped me solve the issue: 
                    // https://stackoverflow.com/questions/30194562/json-parse-not-working
                var messages = JSON.parse(JSON.parse(data)); 
                
                var msgOutput = "";
                for(var i = 0; i < messages.length; i++) {
                    var msg = messages[i]; 
                    
                    msgOutput += "[" + msg.msgtime + "] " + msg.username + ": " + msg.msg + "\n";  // formatting output properly       
                }       
                
                document.getElementById("result").value = msgOutput; // displaying formatted output in text area
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

