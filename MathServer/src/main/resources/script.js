console.log("Hello world!");


function plus(a, b) {
    request({url: "/plus?p1="+a+"&p2="+b, verb: "GET"})
            .then(data => {
                console.log("success: "+a+"+"+b+" is " + data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
    
}

for (var i=0; i<50; i++) {
    plus(i, i);
}




