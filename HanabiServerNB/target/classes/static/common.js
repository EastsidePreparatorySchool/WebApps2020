function request(obj) {
    return new Promise((resolve, reject) => {
        let xhr = new XMLHttpRequest();
        xhr.open(obj.method || "GET", obj.url);
       
        xhr.onload = () => {
            if (xhr.status >= 200 && xhr.status < 300) {
                resolve(xhr.response);
            } else {
                reject(xhr.statusText);
            }
        };
        xhr.onerror = () => reject(xhr.statusText);
        xhr.setRequestHeader("tabid", sessionStorage.getItem("tabid")); 
        //console.log(sessionStorage);
        xhr.send(obj.body);
    });
};

/*
function updateUser() {
    request({url: "user", verb: "GET"})
            .then(username=> {
                console.log(username);
                document.getElementById("displayLogIn").innerHTML = "Logged in as " + username;
                console.log(document.getElementById("displayLogIn").value);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}
 * 
 */

function switchUser(){
   window.location.href='/loginextra?tabid=' + sessionStorage.getItem("tabid");
}

function getheaders() {
    request({url: "getheaders", verb: "GET"})
            .then(result=> {
                console.log(result);
            })
            .catch(error => {
                console.log("error: " + error);
            });
        }

// make an id for the tab that we are in

if (sessionStorage.getItem("tabid") === null) {
    //seed = new Date().getTime();
    id = String(Math.random());
    console.log(id);
    sessionStorage.setItem("tabid", id);
}

<<<<<<< Updated upstream
request({url:"/load"})
        .then(data=>{console.log(data);})
        .catch(error=>{console.log(error);});
    
   // updateUser();
=======
request({url: "/load"})
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.log(error);
        });


getUsername();


function getUsername() {
    request({url: "/getUsername", method: "GET"})
            .then(username => {
                console.log("function getUsername(): " + username);
                document.getElementById("displayLogIn").innerHTML = "Logged in as " + username + ".";        
            })
            .catch(error => {
                console.log("function getUsername(): error: " + error);
            });
}

function LogIn() {
    console.log("function LogIn():");

    switchUser();
    request({url: "/getUsername", method: "GET"})
            .then(username => {
                console.log("function getUsername(): " + username);
                document.getElementById("displayLogIn").innerHTML = "Logged in as " + username + ".";
            })
            .catch(error => {
                console.log("function getUsername(): error: " + error);
            });
}

function switchUser() {
    window.location.href = '/loginextra?tabid=' + sessionStorage.getItem("tabid");
}
>>>>>>> Stashed changes
