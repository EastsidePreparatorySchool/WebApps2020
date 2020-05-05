console.log("Hello world!");


function plus(a, b, f) {
    request({url: "/plus?p1="+a+"&p2="+b, verb: "GET"})
            .then(data => {
                console.log("success: "+a+"+"+b+" is " + data);
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
    
}

function updateMessages(f) {
    request({url: "/update_messages", verb: "GET"}).then(data => {
        console.log("Success! Updating Messages");
        
        let table = JSON.parse(data);      
        if (table["msg"] !== "") {
            f(table);
        }
    }).catch(error => {
        console.log("Something borked: "+error);
    });
}
function sendMessage(msg) {
    console.log(msg);
    request({url: "/send_message?msg="+msg, verb: "PUT"}).then(data => {
        console.log("Success! Updating Messages");
    }).catch(error => {
        console.log("Something borked: "+error);
    });
}
function loginUser(username) {
    console.log("Login user "+username);
    
    //let json = JSON.stringify({"username":username, "timestamp":new Date().getTime()});
    let json = username;
    console.log(json);
    
    request({url: "/login_user?username="+json, verb: "PUT"}).then(data => {
        console.log("Success! Logged in user");
        document.getElementById("loginBox").style.display = "none";
        document.getElementById("app").style.display = "block";
    }).catch(error => {
        console.log("Something borked: "+error);
    });
}


function plus_from_input() {
    var p1 = document.getElementById("p1").value;
    var p2 = document.getElementById("p2").value;
    
    plus(p1,p2,function (data) { document.getElementById("result").value = data; });
}

function displayHeaders() {
    request({url: "/headers", verb: "GET"}).then(data => {
        console.log("Headers page accessed");
        
        document.getElementById("result").value = data;
    }).catch(error => {
        console.log("Something borked: "+error);
    });
}

function updateMessagesTextArea() {
    updateMessages(function (table) { document.getElementById("result").value += "\n" + table["msg"] + " -" + table["username"]; });
}
function sendMessageAndUpdateTextArea() {
    var msg = document.getElementById("textIn").value;
    document.getElementById("textIn").value = "";
    
    sendMessage(msg);
    updateMessagesTextArea();
}
function loginUserHTML() {
    var username = document.getElementById("username").value;
    
    loginUser(username);
//    updateMessagesTextArea();
}

var updateMessageInverval = setInterval(function (){updateMessagesTextArea();}, 500);




