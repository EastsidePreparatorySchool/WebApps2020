logIn();

function logIn() {
    request({url: "/login_user?username=" + username, method: "GET"})
            .then(username => {
                console.log(username);
                document.getElementById("displayusername").innerHTML = "Logged in as " + username + ".";
                console.log(document.getElementById("displayusername").value);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

// template functions for updating available games in lobby
function getGames(f) {
    request({url: "/update_games", verb: "GET"})
            .then(data => {
                console.log("success, updating games");
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function getGamesUpdateTable() {
    getMessages(function (data) {
        var table = document.getElementById("gameTable");
        let games = JSON.parse(data);
        for (var i = 0; i < messages.length; i++) {
            var row = table.insertRow(0);
            row.insertCell(0).innerHTML = games[i];
            row.insertCell(1).innerHTML = "<a href='game.html'>Join Game</a>";
        }
    });
}

setInterval(function () {
    getGamesUpdateTable();
}, 200);