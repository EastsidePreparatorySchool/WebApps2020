
//function getUsername() {           
//    request({url: "/getUsername", method: "GET"})
//            .then(username => {
//                console.log("function getUsername(): " + username);
//                document.getElementById("displayLogInLobby").innerHTML = "Logged in as " + username + ".";
//            })
//            .catch(error => {
//                console.log("function getUsername(): error: " + error);
//            });
//}
//
//function LogIn() {
//    console.log("function LogIn():");
//    
//    switchUser();
//    getUsername();
//    request({url: "/getUsername", method: "GET"})
//            .then(username => {
//                console.log("function getUsername(): " + username);
//                document.getElementById("displayLogIn").innerHTML = "Logged in as " + username + ".";
//            })
//            .catch(error => {
//                console.log("function getUsername(): error: " + error);
//            });
//}
//
//function switchUser(){
//    window.location.href='/loginextra?tabid=' + sessionStorage.getItem("tabid");
//}


// template functions for updating available games in lobby
function getGames(f) {
    request({url: "/lobby-games", verb: "GET"})
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
            row.insertCell(1).innerHTML = "<a href='game.html?id=" + i + "'>Join Game</a>";
        }
    });
        
}

getGamesUpdateTable();