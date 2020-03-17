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
        f(data);
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


function plus_from_input() {
    var p1 = document.getElementById("p1").value;
    var p2 = document.getElementById("p2").value;
    
    plus(p1,p2,function (data) { document.getElementById("result").value = data; });
}
function updateMessagesTextArea() {
    updateMessages(function (data) { document.getElementById("result").value = data; });
}
function sendMessageAndUpdateTextArea() {
    var msg = document.getElementById("textIn").value;
    
    sendMessage(msg);
    updateMessagesTextArea();
}

for (var i=0; i<50; i++) {
    plus(i, i);
}




