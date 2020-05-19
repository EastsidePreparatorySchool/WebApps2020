
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
        xhr.send(obj.body);
    });
};



//function output (message) {
//    //output("promise request");
//    document.getElementById("output").innerHTML += message + "<br>";
//}

// make an id for the tab that we are in

if (sessionStorage.getItem("tabid") === null) {
    //seed = new Date().getTime();
    id = String(Math.random());
    console.log(id);
    sessionStorage.setItem("tabid", id);
}

//output("ready");

request({url:"/load"})
        .then(data=>{console.log(data);})
        .catch(error=>{console.log(error);});
    