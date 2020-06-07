gameid = 0;

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
       //    row.insertCell(1).innerHTML = "<a href='game.html?id=" + i + "'>Join Game</a>";
            row.insertCell(1).innerHTML = "<button onlick='joinGame()'>Join Game</button>";
        }
    });
}

getGamesUpdateTable();

function joinGame() {
    request({url: "/join_game?gameid=" + , verb: "GET"})
            .then(data => {
                console.log("success, updating games");
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}