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

setInterval(function () {
    getMessagesTextArea();
}, 100);

function getMessages(f) {
    textarea = document.getElementById("result");
    request({url: "/update_messages", verb: "GET"})
            .then(data => {
                console.log("success!");
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function getMessagesTextArea() {
    getMessages(function (data) {
        var msg = JSON.parse(data).toString();
        msg = msg.replace(/~~/g, " \n");
        document.getElementById("result").value = msg;
    });
}

setInterval(function () {
    getMessagesTextArea();
}, 100);

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
