console.log("Hello world!");


function get_new_messages(f) {
    request({url: "/getnew", verb: "GET"})
            .then(data => {
                console.log("success");
                f(data);
            })
            .catch(error => {
                console.log("error: " + error);
            });
    
}

function add_new_message(m) {
    console.log("trying to add message");
    request({url: "/addmsg?message=" + m, verb: "GET"})
            .then(data => {
                console.log("success");
            })
            .catch(error =>{
                console.log("error: " + error);
            });
}

function show_new_messages(){
    get_new_messages(function (data) {
        document.getElementById("result").value = data;
    });
}

function add_new_message_from_button(){
    add_new_message(
            document.getElementById("input").value
            );
    document.getElementById("input").value = "";
    show_new_messages();
}



