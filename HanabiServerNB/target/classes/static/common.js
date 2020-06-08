let playerusername;
userid = "";
usernameForJoinGame = "";

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
}
;

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

//function switchUser(){
//    window.location.href='/loginextra?tabid=' + sessionStorage.getItem("tabid");
//}

function getheaders() {
    request({url: "getheaders", method: "GET"})
            .then(result => {
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

request({url: "/load"})
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.log(error);
        });



function getUsername() {
    request({url: "/getUsername", method: "GET"})
            .then(username => {
                username = JSON.parse(username);
                console.log("function getUsername(): " + username);
                if (username === "null") {
                    document.getElementById("displayLogIn").innerText = "Not logged in.";
                } else {
                    userid = username + sessionStorage.getItem("tabid");
                    usernameForJoinGame = username;
                    playerusername = username;
                    document.getElementById("displayLogIn").innerText = "Logged in as " + username + ".";
                }
            })
            .catch(error => {
                console.log("function getUsername(): error: " + error);
            });
}

function LogIn() {
    console.log("function LogIn():");
    window.location.href = '/loginextra?tabid=' + sessionStorage.getItem("tabid");
}


//
// run this once
//

getUsername();


// template functions for updating available games in lobby
function getGames(f) {
    request({url: "/lobby-games", method: "GET"})
            .then(data => {
                console.log("success, updating games");
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}


function getGamesUpdateTable() {
    getGames(function (data) {
        var table = document.getElementById("gameTable");
        let games = JSON.parse(data);
        for (var i = 0; i < games.length; i++) {
            var row = table.insertRow(1);
            row.insertCell(0).innerHTML = games[i].gameData.name;
            row.insertCell(1).innerHTML = "<button onclick='joinGame(" + i + ")'>Join Game " + i +"</button>";
        }
    });
}

//getGamesUpdateTable();

function joinGame(gameid) {
    console.log("i am here!!");
    console.log(userid);
    
    request({url: "/join_game?gameid=" + gameid + "&userid=" + userid + "&username=" + usernameForJoinGame, method: "PUT"})
            .then(data => {
                console.log("success, joining game");
                window.location.href = '/game.html?id=' + gameid;
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

