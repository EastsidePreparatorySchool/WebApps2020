
let DEBUG = true;
let game;
let debugDiv = document.getElementById("debug");


var player1 = document.getElementById("playerLabel1");
var player2 =  document.getElementById("playerLabel2");
var player3 = document.getElementById("playerLabel3");
var player4 = document.getElementById("playerLabel4");
var player5 = document.getElementById("playerLabel5"); 
var playerId = getPlayeriD();
console.log(playerId);
var updated = false;
var discarded = false;

setInterval(function () {
    if (DEBUG) {
        request({url: "/update?gid=" + 0, method: "GET"})
                .then(data => {
                    game = JSON.parse(data);
                    render_update(data);
                    console.log("Update requested");
                })
                .catch(error => {
                    console.log("error: " + error);
                });
//        let card = JSON.stringify({color: "Purple", number: 2, played: false, discarded: false});
//        let turn = JSON.stringify({gameId: 0, isDiscard: true, isPlay: false, isHint: false, playerTo: "", hintType: "", hint: ""});
//        request({url: "/turn?turn=" + turn + "&card=" + card, method: "GET"})
//                .then(data => {
//                    console.log(data);
//                })
//                .catch(error => {
//                    console.log("error: " + error);
//                });
    } else {
        request({url: "/update?gid=" + a, method: "GET"}) // "a" needs to be a game ID
                .then(data => {
                    console.log("update received");
                    render_update(data);
                })
                .catch(error => {
                    console.log("error: " + error);
                });
    }
}, 1000);

function updateCardInfo(playerNumber, slotNumber, newColor, newNumber) {
    //player number and slot number select what card is going to be changed, slot number is from left to right (1 for left, 2 is middle, etc)
    var playerCard = document.getElementById("player" + playerNumber + "Card" + slotNumber);
    playerCard.style.color = newColor;
    playerCard.innerHTML = newNumber;
    updated = true;
    return updated;
}

function RandomizeCards() { //function to randomize cards from 1-5 and colors wise
    var colors = ["red", "blue", "purple", "green", "yellow"];
    for (var i = 1; i < 7; i++) { //iterate thru players
        for (var j = 1; j < 4; j++) { //iterate thru player slots
            updateCardInfo(i, j, colors[Math.floor(Math.random() * colors.length)], Math.round((Math.random() * 4 + 1)));
        }
    }
}

function displayUsername(playerNumber, username) {
    document.getElementById("playerLabel" + playerNumber).innerHTML = username;
}

//0 is neither 1 is play 2 is discard
var playdiscard = 0;

//blurs card select button if true
var buttonblur = true;
console.log(buttonblur);

function blurButtons() {
    document.getElementById("select1").setAttribute('disabled', 'disabled');
    document.getElementById("select2").setAttribute('disabled', 'disabled');
    document.getElementById("select3").setAttribute('disabled', 'disabled');
    document.getElementById("select4").setAttribute('disabled', 'disabled');
    document.getElementById("select5").setAttribute('disabled', 'disabled');
}

function unblurButtons() {
    document.getElementById("select1").removeAttribute('disabled');
    document.getElementById("select2").removeAttribute('disabled');
    document.getElementById("select3").removeAttribute('disabled');
    document.getElementById("select4").removeAttribute('disabled');
    document.getElementById("select5").removeAttribute('disabled');
}

function blurPileButtons() {
    document.getElementById("redpilebutton").setAttribute('disabled', 'disabled');
    document.getElementById("greenpilebutton").setAttribute('disabled', 'disabled');
    document.getElementById("yellowpilebutton").setAttribute('disabled', 'disabled');
    document.getElementById("bluepilebutton").setAttribute('disabled', 'disabled');
    document.getElementById("purplepilebutton").setAttribute('disabled', 'disabled');
}

function unblurPileButtons() {
    document.getElementById("redpilebutton").removeAttribute('disabled');
    document.getElementById("greenpilebutton").removeAttribute('disabled');
    document.getElementById("yellowpilebutton").removeAttribute('disabled');
    document.getElementById("bluepilebutton").removeAttribute('disabled');
    document.getElementById("purplepilebutton").removeAttribute('disabled');
}

function selectPlay() {
    playdiscard = 1;
    unblurButtons();
    document.getElementById("playbutton").setAttribute('disabled', 'disabled');
    document.getElementById("discardbutton").setAttribute('disabled', 'disabled');
}

function selectDiscard() {
    playdiscard = 2;
    unblurButtons();
    document.getElementById("playbutton").setAttribute('disabled', 'disabled');
    document.getElementById("discardbutton").setAttribute('disabled', 'disabled');
}

//stores information about the selected card
var selectedCard = 0;

//id tells you which button you pressed (1-5)
function selectCard(id) {
    //takes the values of the card and stores them into the variable above
    //selectedCard = hand.id.value (guess of how the hand class works)
    if (playdiscard === 1) {
        play(id);
    } else if (playdiscard === 2) {
        discard(id);
    }
    playdiscard = 0;
    blurButtons();

}

var pile = 0;
function selectPile(num) {
    pile = num;
    blurPileButtons();
    document.getElementById("playbutton").removeAttribute('disabled');
    document.getElementById("discardbutton").removeAttribute('disabled');
}
function play(id) {
    //select pile
    unblurPileButtons();

    //remove card
    //hand.remove(id)

    //add value to pile
    //pile.value++;

    //draw new card
    //hand.draw();

    //end turn
    pile = 0;
}


function getPlayeriD() {
    if (DEBUG) {
        request({url: "/info", method: "GET"})
                .then(data => {
                    playerid = JSON.parse(data);
                    console.log(playerid);
                })
                .catch(error => {
                    console.log("error: " + error);
                });
            }
            }



function discard(card, gameID, playerID) {
    //remove card
    //add card to discard pile
    //draw new card
    //TODO: end turn
    card = JSON.stringify(card);
    if (!DEBUG) {
        request({url: "/discard?to_discard=" + card + "&game_id=" + gameID + "&player_id=" + playerID, method: "PUT"}) // "a" needs to be a game ID
                .then(data => {
                    console.log("Discarded:");

                    discarded = true;
                    console.log(data);
                    return discarded;

                    console.log(data);
                })
                .catch(error => {
                    console.log("Discard error: " + error);
                });
    } else {
        console.log("DISCARDING IS WORKING (debug)");
        request({url: "/discard?to_discard=" + card + "&game_id=" + 0 + "&player_id=" + 0, method: "PUT"}) // "a" needs to be a game ID
                .then(data => {
                    console.log("Discarded:");
                    console.log(data);
                })
                .catch(error => {
                    console.log("Discard error: " + error);
                });
    }
    document.getElementById("playbutton").removeAttribute('disabled');
    document.getElementById("discardbutton").removeAttribute('disabled');

setInterval(getNew, 300);

// storing ids of all clue giving buttons
var clueButtons = [["P1clue", "P2clue", "P3clue", "P4clue", "P5clue"], ["redClue", "greenClue", "yellowClue", "blueClue", "purpleClue", "1clue", "2clue", "3clue", "4clue", "5clue"]];

// disable the other clue buttons once one is clicked
function disable(num, id) {
    
    console.log(num);
    for (var i = 0; i < clueButtons[num].length; i++) {
        if ((clueButtons[num][i]).localeCompare(id) != 0) {
            document.getElementById(clueButtons[num][i]).setAttribute("disabled", "disabled");
        }
    }
    console.log(id);
}

// reset disabled clue buttons
function reenableClueBtns() {
    for(var i=0; i< clueButtons.length; i++) {
        var clueBtnIDs = clueButtons[i];
        for(var j=0; j < clueBtnIDs.length; j++) {
            document.getElementById(clueButtons[i][j]).removeAttribute("disabled");
        }
    }
}

// send clues to server
// TODO: confirm player ID. assuming player order in game data matches display ID
function giveClue() {
    var toPlayer = -1;
    for(var i=0; i<clueButtons[0].length;i++) {
        if (!document.getElementById(clueButtons[0][i]).disabled) {
            toPlayer++;
            break;
        }
    }

    var hintIndex=0;
    for(var i=0; i< clueButtons[1].length;i++) {
        hintIndex++;
        if(!document.getElementById(clueButtons[0][i]).disabled) {
            break;
        }
    }


    var hintObject = {isColor: hintIndex > 5, playerFromId: "", playerToId: game.players[toPlayer].myUser.myID, hintContent: clueButtons[1][hintIndex].slice(0,-4)};
    print("Sending hint: "+JSON.stringify(hintObject));
    request({url: "/give_hint?hint="+JSON.stringify(hintObject), method: "PUT"}).then(data => {
        console.log("Sent: "+JSON.stringify(hintObject));
    }).catch(error => {
        console.log("Error: "+error);
    })
}


// optional chat feature (Aybala)

function sendMsg() {
    console.log("made it sendMsg");
    var a = document.getElementById("msgBox").value;
    request({url: "/send?msg=" + a, method: "PUT"})
            .then(data => {
                console.log(a);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

function getNew() {
    request({url: "/get", method: "GET"})
            .then(data => {
                var messages = JSON.parse(JSON.parse(data));
                var msgOutput = "";
                for (var i = 0; i < messages.length; i++) {
                    var msg = messages[i];
                    msgOutput += msg.username + ": " + msg.msg + "\n";  // formatting output properly       
                }

                document.getElementById("chatbox").value = msgOutput; // displaying formatted output in text area
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

setInterval(getNew, 300);

logIn();

function logIn() {
    request({url: "/login_user?username=" + username, method: "GET"})
            .then(username => {
                //  document.getElementById("displayLogIn").innerHTML = "Logged in as " + username + ".";
                console.log(username);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}

// sends message by just pressing enter
var x = document.getElementById("msgBox");
//taken from w3schools
x.addEventListener("keyup", function (event) {
    if (event.keyCode === 13) {
        event.preventDefault();
        sendMsg();
        x.value = "";
    }
});

function test(updated, discarded) {
    /*
    var c = document.getElementById("msgbox");
    var testctx = c.getContext("2d");
     * 
     */

    
    setTimeout(updateCardInfo(1, 2, "purple", 3), 300);
    setTimeout(updateCardInfo(1, 3, "blue", 1), 300);
    console.log("updated =" + updated);
    if (updated === true){
     console.log("updating cards");
    }
    setTimeout(discard(game.players[0].myHand.cards[0]), 300);
    console.log("dicarded = " + discarded);
    
    if (discarded === true){
        console.log("discarding cards");
        /*
       testctx.beginPath();
      testctx.lineWidth = "6";
      testctx.strokeStyle = "green";
      testctx.rect(5, 5, 290, 140);
      testctx.stroke();
         * 
         */
    }
    setTimeout(play(1), 300);
    console.log("playing card");
//function getUsername() {           
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
//function LogIn() {
//    console.log("function LogIn():");
//    
//    switchUser();    
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


function test() {
    //add way to give clue
    setTimeout(updateCardInfo(1, 2, "purple", 3), 300);
    console.log("updating cards");
    setTimeout(discard(game.players[0].myHand.cards[0]), 300);
    console.log("discarding cards");
    setTimeout(play(1), 300);
    console.log("playing card");

    //no client code for giving clue
    // setTimeout(giveClue(1, 1), 300);
    //console.log("giving clue");

    // Giving clue is done manually (it relies on input buttons)
    //// no client code for giving clue
    //// setTimeout(giveClue(), 300);
    //// console.log("giving clue");

}

function render_update(data) {
    let update_data = JSON.parse(data);
    //console.log(update_data);
    //debugDiv.innerHTML = data;
    //debugDiv.innerHTML = update_data.players[0].myUser.username;
    render_user_cards(update_data.players);

    getUsername();

}

function render_user_cards(playerArr) {
    console.log("rendering usernames");
    for (var numPlayer = 0; numPlayer < playerArr.length; numPlayer++) {

        let cp = playerArr[numPlayer];

        console.log('at player ' + numPlayer);
        document.getElementById("playerLabel" + (numPlayer + 2)).innerText = cp.myUser.username;

        for (var i = 0; i <= 2; i++) {
            let card = document.getElementById("player" + (numPlayer + 2) + "Card" + (i + 1));
            console.log(playerArr[numPlayer]);
            card.innerText = cp.myHand.cards[i].number;
            card.style.color = cp.myHand.cards[i].color;
            card.style.fontSize = "90px";
            card.style.textAlign = "center";
            card.style.lineHeight = "100px";
            card.style.fontFamily = "sans-serif";
            card.style.fontWeight = "700";
        }

    }
}