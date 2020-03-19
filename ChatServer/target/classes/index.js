console.log("Hello world!");


function get_new_messages(f) {
    request({url: "/getnew", verb: "GET"})
            .then(data => {
                console.log("success");
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
    
}

function add_new_message(m) {
    console.log("trying to add message");
    request({url: "/addmsg?message=" + m, verb: "GET"})
            .then(data => {
                console.log("success");
            })
            .catch(error =>{
                console.log("error: " + error);
            });
}

function get_username(f) {
    console.log("getting username");
    request({url: "/getusr", verb: "GET"})
            .then(data => {
                console.log("user is " + data);
                f(data);
            })
            .catch(error =>{
                console.log("error: " + error);
            });
}

function show_new_messages(){
    get_new_messages(function (data) {
        document.getElementById("result").value = data;
    });
}

function add_new_message_from_button(){
    let val = document.getElementById("input");
    if(val.value !== ""){
        add_new_message(val.value);
        val.value = "";
    }
}

function login(f){
    console.log("user wants to log in");
    let new_usr = prompt("What is your username?");
    request({url: "/login?usr=" + new_usr, verb : "GET"})
            .then(data => {
                console.log("success, " + data);
                document.getElementById("title-usr").innerHTML = data;
                //location.reload();
            })
            .catch(error => {
                console.log("error: " + error);
            });
}