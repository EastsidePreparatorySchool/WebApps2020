let DEBUG = true; // what's up with this? there's duplicate code when it get's used... ~KY
let game;
var thisGameID = -1;
var myPlayerID;
let debugDiv = document.getElementById("debug");

let MYDATA;

let thisGameIDx = new URLSearchParams(window.location.search).get('id');
var updated = false;
var discarded = false;

setInterval(function () {
    if (DEBUG) {
        request({url: "/update?gid=" + thisGameIDx, method: "GET"})
                .then(data => {
                    if (thisGameID === -1) {
                        let myUser = JSON.parse(data);
                        thisGameID = myUser.InGameID;
                        myPlayerID = myUser.attachedPlayerID;
                    } else {
                        game = JSON.parse(data);
                        render_update(data);
                        console.log("Update requested");
                    }
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
var selectedCard = -1;

//id tells you which button you pressed (1-5)
function selectCard(id) {
    //takes the values of the card and stores them into the variable above
    //selectedCard = hand.id.value (guess of how the hand class works)
    selectedCard = id;
    if (playdiscard === 1) {
        if (pile !== 0) {
            play(selectedCard);
        }
        blurButtons();
    } else if (playdiscard === 2) {
        discard(id);
        playdiscard = 0;
        blurButtons();
    }

}

var pile = 0;
function selectPile(txt) {
    var colors = ["smurple", "red", "green", "yellow", "blue", "purple"];
    pile = colors[txt];
    blurPileButtons();

    if (playdiscard === 1 && pile !== 0 && selectedCard !== -1) {
        play(selectedCard);
    }
    document.getElementById("playbutton").removeAttribute('disabled');
    document.getElementById("discardbutton").removeAttribute('disabled');
}
function play(id) {
    //select pile
    unblurPileButtons();
    if (!DEBUG) {
        request({url: "/play_card?pile=" + pile + "&playerID=" + myPlayerID + "&cardnumber=" + id + "&gameID=" + thisGameID, verb: "PUT"})
                .then(data => {
                    console.log("Play card should work")
                    var s = data;
                    if (s == 234) {
                        //do Jonathans alert or something
                        alert("Can't let you play that card, star fox");
                    }
                })
                .catch(error => {
                    console.log("error, play card not working: " + error);
                });
    } else {
        console.log("Playing card should be running (debug)");
        request({url: "/play_card?pile=" + pile + "&playerID=" + 0 + "&cardnumber=" + cardindex + "&gameID=" + 0, verb: "PUT"})
                .then(data => {
                    console.log("Play :");
                    console.log(data);
                    var s = data;
                    if (s == 234) {
                        //do Jonathans alert or something
                    }
                })
                .catch(error => {
                    console.log("error, play card not working: " + error);
                });
    }

    pile = 0;
    selectedCard = -1;
    playdiscard = 0;

    document.getElementById("playbutton").removeAttribute('disabled');
    document.getElementById("discardbutton").removeAttribute('disabled');
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

}



document.getElementById("playbutton").removeAttribute('disabled');
document.getElementById("discardbutton").removeAttribute('disabled');

setInterval(getNew, 300);

// storing ids of all clue giving buttons
var clueButtons = [["P1clue", "P2clue", "P3clue", "P4clue", "P5clue"], ["redClue", "greenClue", "yellowClue", "blueClue", "purpleClue", "1clue", "2clue", "3clue", "4clue", "5clue"]];

var playerToGiveClue;
var isClueColor;
var clueContent;

// disable the other clue buttons once one is clicked
function disable(num, id) {

    console.log(num);
    for (var i = 0; i < clueButtons[num].length; i++) {
        if (i !== id) {
            document.getElementById(clueButtons[num][i]).setAttribute("disabled", "disabled");
        }
    }
    console.log(id);
}

// set player to give clue
function setPlayerToGiveClue(playerNum) {
    playerToGiveClue = playerNum;

    disable(0, playerNum - 1);
}

// set clue content
function setClueContent(type, value) {
    isClueColor = type;
    let colorValues = ["red", "green", "yellow", "blue", "purple"];
    if (type) {
        clueContent = colorValues[value];
    } else {
        clueContent = value;
    }

    disable(1, type ? value : 5 + value);
}

// reset disabled clue buttons
function reenableClueBtns() {
    for (var i = 0; i < clueButtons.length; i++) {
        var clueBtnIDs = clueButtons[i];
        for (var j = 0; j < clueBtnIDs.length; j++) {
            document.getElementById(clueButtons[i][j]).removeAttribute("disabled");
        }
    }
}

// send clues to server
// TODO: confirm player ID. assuming player order in game data matches display ID
function giveClue() {
    // var toPlayer = -1;
    // for (var i = 0; i < clueButtons[0].length; i++) {
    //     if (!document.getElementById(clueButtons[0][i]).disabled) {
    //         toPlayer++;
    //         break;
    //     }
    // }

    // var hintIndex = 0;
    // for (var i = 0; i < clueButtons[1].length; i++) {
    //     hintIndex++;
    //     if (!document.getElementById(clueButtons[0][i]).disabled) {
    //         break;
    //     }
    // }

    var hintObject = {isColor: isClueColor, playerFromId: "", playerToId: game.players[playerToGiveClue].myID, hintContent: clueContent};
    console.log("Sending hint: " + JSON.stringify(hintObject));
    request({url: "/give_hint?hint=" + JSON.stringify(hintObject) + "&gid=" + thisGameID, method: "PUT"})
            .then(data => {
                console.log("Sent: " + JSON.stringify(hintObject));
            }).catch(error => {
        console.log("Error: " + error);
    });

    reenableClueBtns();
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

function test(playerArr) {
    setTimeout(updateCardInfo(1, 2, "purple", 3), 300);
    setTimeout(updateCardInfo(1, 3, "blue", 1), 300);
    var result = true;
    var player1Card2 = document.getElementById("player1Card2");
    var player1Card3 = document.getElementById("player1Card3");
    console.log("player1card2=" + player1Card2.innerText);
    console.log("player1card2=" + player1Card3.innerText);
    result = result && (player1Card2.innerText === "3");
    result = result && (player1Card3.innerText === "1");
    if (result === true) {
        // document.write("update test passed!");
        console.log("update test passed");
    }



    console.log("updating cards");

    setTimeout(discard(("blue", 1), 0, "foo1"), 300);
    result = result && (player1Card3.innerText !== "1");
    if (result === true) {
        //document.write("discard test passed!");
        console.log("discard test passed");
    }


    pile = "purple";
    setTimeout(play(0), 300);
    pile = 0;
    result = result && (player1Card2.innerText !== "3");

    console.log("playing card");
    if (result === true) {
        //  document.write("play card test passed!");
        console.log("play card test passed");
    }

    setTimeout(() => {
        playerToGiveClue = 0;
        isClueColor = false;
        clueContent = 5;
        giveClue();
        // document.write("give clue test passed");
    }, 300);
    console.log("Gave clue to player 0");
    if (result === true) {
        console.log("whole test passed");
        document.write("whole test passed");
    }
}


function render_update(data) {
    let update_data = JSON.parse(data);
    console.log(update_data);
    //debugDiv.innerHTML = data;
    //debugDiv.innerHTML = update_data.players[0].myUser.Name;
    render_user_cards(update_data.players);
    //getUsername();

}

function render_user_cards(playerArr) {
    //console.log("rendering usernames");
    for (var numPlayer = 0; numPlayer < playerArr.length; numPlayer++) {

        let cp = playerArr[numPlayer];

        //console.log('at player ' + numPlayer);


        usrfromserver = playerusername.split('"')[1];

        if (usrfromserver === cp.myUser.Name) {

            console.log(cp.myUser.Name + ' is me');
            document.getElementById("player" + (numPlayer + 2) + "Cards").innerHTML = "You are Player " + numPlayer + "!";

            MYDATA = cp;

            render_my_cards();

        } else {

            console.log("else");
            document.getElementById("playerLabel" + (numPlayer + 2)).innerText = cp.myUser.Name;
            console.log("did playerlabel");
            document.getElementById("P" + (numPlayer + 2) + "clue").innerText = cp.myUser.Name;
            console.log("did buttonlabel");
            for (var i = 0; i <= 2; i++) {
                render_card("player" + (numPlayer + 2) + "Card" + (i + 1), cp.myHand.cards[i].color, cp.myHand.cards[i].number);
            }

        }

    }
}

function render_card(element_id, color, number) {
    let card = document.getElementById(element_id);
    //console.log(playerArr[numPlayer]);
    card.innerText = number;
    card.style.color = color;
    card.style.fontSize = "90px";
    card.style.textAlign = "center";
    card.style.lineHeight = "100px";
    card.style.fontFamily = "sans-serif";
    card.style.fontWeight = "700";
}

function render_my_cards() {
    console.log(MYDATA);

    let hints = MYDATA.myHints;

    let hand = MYDATA.myHand.cards;

    let displayedHand = [
        {"color": "black", "number": "?"},
        {"color": "black", "number": "?"},
        {"color": "black", "number": "?"}
    ];

    hints.forEach(function (hint) {
        console.log(hint);


        let cardNo = 0;

        hand.forEach(function (card) {

            if (card.color === hint.hintContent) {
                displayedHand[cardNo].color = hint.hintContent;
            }

            if (card.number.toString() === hint.hintContent) {
                displayedHand[cardNo].number = hint.hintContent;
            }

            cardNo++;

        });


    });

    console.log(displayedHand);


    let index = 1;
    displayedHand.forEach(function (card) {
        console.log("player1Card" + index);
        render_card("player1Card" + index, card.color, card.number);

        index++;
    });

}