let login_this_load = false;

function get_new_messages(f) {
    request({url: "/getnew", verb: "GET"})
            .then(data => {
                console.log("success/new messages/get");
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
                console.log("success/new messages/add");
            })
            .catch(error => {
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
            .catch(error => {
                console.log("error: " + error);
            });
}

function show_new_messages() {
    get_new_messages(function (data) {
        document.getElementById("result").value = data;
    });
}

function add_new_message_from_button() {
    let val = document.getElementById("input");
    if (val.value !== "") {
        add_new_message(val.value);
        val.value = "";
    }
}

function login() {
    console.log("user wants to log in");
    let new_usr = prompt("Please log in to ChatServer. Enter username:");

    request({url: "/login?usr=" + new_usr, verb: "GET"})
            .then(data => {
                console.log("success/login, data: " + data);
                alert("You are now logged in as " + data + ". Your browser will now remember your login.");
                set_user_namebox(data);
                login_this_load = true;
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function logout() {
    get_username(function (data) {
        console.log(data + " wants to log out");
    });
    request({url: "/logout", verb: "GET"})
            .then(data => {
                console.log("success/logout, data: " + data);
                location.reload(); //reload page
            })
            .catch(error =>{
                console.log("error: " + error);
            });
}

function refresh_user_namebox() {
    get_username(function (data) {
        set_user_namebox(data);
    });
}

function set_user_namebox(name) {
    console.log("set_user_namebox called with parameter " + name);
    document.getElementById("usr-namebox").innerHTML = "Logged in as " + name + ". <button onclick='logout()'>Switch User</button>";
    if(name === "admin") document.getElementById("usr-namebox").innerHTML += "  <button onclick='clear_chat()'>Clear Chat</button>";
}

function clear_chat(){
    request({url: "/clear", verb: "GET"})
            .then(data => {
                console.log("success/clear, data: " + data);
                location.reload(); //reload page
            })
            .catch(error =>{
                console.log("error: " + error);
            });
}

//executes when the user loads the page
get_username(function (data) {
    if (data === "unknown") {
        login();
    } else if (login_this_load === false) {
        set_user_namebox(data);
    }
});

setInterval(show_new_messages, 500);

//for send when enter is pressed
let input = document.getElementById("input");
input.addEventListener("keyup", function(event) {
    if(event.keyCode === 13) {
        event.preventDefault();
        document.getElementById("sendbtn").click();
    }
});