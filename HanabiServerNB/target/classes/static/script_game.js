
function updateCardInfo(playerNumber, slotNumber, newColor, newNumber) {
    //player number and slot number select what card is going to be changed, slot number is from left to right (1 for left, 2 is middle, etc)
    var playerCard = document.getElementById("player" + playerNumber + "Card" + slotNumber);
    playerCard.style.color = newColor;
    playerCard.innerHTML = newNumber;
}

function RandomizeCards() { //function to randomize cards from 1-5 and colors wise
    var colors = ["red", "blue", "purple", "green", "yellow"];
    for (var i = 1; i < 7; i++) { //iterate thru players
        for (var j = 1; j < 4; j++) { //iterate thru player slots
            updateCardInfo(i, j, colors[Math.floor(Math.random() * colors.length)], Math.round((Math.random() * 4 + 1)));
        }
    }
}

function displayUsername(playerNumber, username){
    document.getElementById("playerLabel"+playerNumber).innerHTML = username;
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
    if(playdiscard === 1) {
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

function discard(card) {
    //remove card
    //add card to discard pile
    //draw new card
    //end turn
    document.getElementById("playbutton").removeAttribute('disabled');
    document.getElementById("discardbutton").removeAttribute('disabled');
}
setInterval(getNew, 300);


// disable the other clue buttons once one is clicked
function disable(num, id) {
    // storing ids of all clue giving buttons
    var clueButtons = [["P1clue", "P2clue", "P3clue", "P4clue", "P5clue"], ["redClue", "greenClue", "yellowClue", "blueClue", "purpleClue", "1clue", "2clue", "3clue", "4clue", "5clue"]];
    console.log(num);
    for (var i = 0; i < clueButtons[num].length; i++) {
        if ((clueButtons[num][i]).localeCompare(id) != 0) {
            document.getElementById(clueButtons[num][i]).setAttribute("disabled", "disabled");
        }
    }
    console.log(id);
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
                for(var i = 0; i < messages.length; i++) {
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
                console.log(username);
                document.getElementById("displayusername").innerHTML = "Logged in as " + username + ".";
                console.log(document.getElementById("displayusername").value);
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
