console.log("Hello world!");
function login() {
    var userID = alert(prompt("login info:", "type here")); //doesn't pop up yet
    if (userID == null || userID == "") {
        txt = "no login given";
    } else {
         request({url: "/login", body: userID, verb: "GET"})
            .then(data => {          
            })
            .catch(error => {
                console.log("error: " + error);
            });
    }
}

function plus(a, b, f) {
    request({url: "/plus?p1=" + a + "&p2=" + b, verb: "GET"})
            .then(data => {
                console.log("success: " + a + "+" + b + " is " + data);
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function plus_from_input() {
    var p1 = document.getElementById("p1").value;
    var p2 = document.getElementById("p2").value;

    plus(p1, p2, function (data) {
        document.getElementById("result").value = data;
    });
}

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
