console.log("Hello world!");
function login() {
    var userID = prompt("login info:", "type here");
     document.getElementById("loginButton").disabled = true;
    if (userID == null || userID == "") {
        txt = "no login given";
    } else {

        request({url: "/login", body: userID, verb: "PUT"})
                .then(data => {
                })
                .catch(error => {
                    console.log("error: " + error);
                });
    }
}

setInterval(function (){getMessagesTextArea();}, 100);

function getMessages(f) {
    textarea = document.getElementById("result");
    request({url: "/update_messages", verb: "GET"})
            .then(data => {
                console.log("success, updating messages");
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function getMessagesTextArea() {
    getMessages(function (data) {
        document.getElementById("result").value = data;
    });
}

function sendMessages(msg) {
    request({url: "/send?message=" + msg, verb: "PUT"})
            .then(data => {
                console.log("success: message is" + msg);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function sendMessagesTextArea() {
    sendMessages(document.getElementById("messageSender").value);

}
