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
            .catch(error =>{
                console.log("error: " + error);
            });
}

function get_username(f) {
    console.log("getting username");
    request({url: "/getusr", verb: "GET"})
            .then(data => {
                console.log("user is " + data);
                f (data);
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
    let new_usr = prompt("Please log in to ChatServer. Enter username:");
    
    if(new_usr.toString().toUpperCase() === "ADMIN"){
        alert("You can't be admin.");
        logout();
        return;
    }
    
    request({url: "/login?usr=" + new_usr, verb : "GET"})
            .then(data => {
                console.log("success/login, data: " + data);
                refresh_user_namebox();
                alert("You are now logged in as " + data + ". Your browser should now remember your login.");
                f(data);
                refresh_user_namebox();
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function logout(){
    get_username(function(data){
        console.log(data + " wants to log out");
    });
    request({url : "/logout", verb : "GET"})
            .then(data => {
                console.log("success/logout, data: " + data);
                location.reload(); //reload page
            })
}

function refresh_user_namebox(){
    get_username(function (data){
        document.getElementById("usr-namebox").innerHTML = "Logged in as " + data;
    });
}

//executes when the user loads the page


get_username(function(data){
    if(data === "unknown"){
        login();
        refresh_user_namebox();
    }
});

setInterval(show_new_messages, 500);