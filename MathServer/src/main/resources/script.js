console.log("Hello world!");


function plus(a, b, f) {
    request({url: "/plus?p1="+a+"&p2="+b, verb: "GET"})
            .then(data => {
                console.log("success: "+a+"+"+b+" is " + data);
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
    
}


function plus_from_input() {
    var p1 = document.getElementById("p1").value;
    var p2 = document.getElementById("p2").value;
    
    plus(p1,p2,function (data) { document.getElementById("result").value = data; });
}

//for (var i=0; i<50; i++) {
//    plus(i, i);
//}



function getMessages(){
    
    
    textarea = document.getElementById("result");
    request({url: "/chat?message="+message+", verb: GET"})
            .then(data => {
                console.log("success: +message is" + data);
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
    textarea.value = message;
}

function sendMessages(){
   
    message = document.getElementById("messageSender"); 
    request({url: "/send?message="+message+", verb: GET"})
            .then(data => {
                console.log("success: +message is" + data);
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
}




