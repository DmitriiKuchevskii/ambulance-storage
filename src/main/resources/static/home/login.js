document.getElementById("submitBtn").addEventListener("click", login);
var xhttp = new XMLHttpRequest();
function login(){
    let logpass = {username:"", password:""};
    logpass.username = document.querySelector("input[name='uname']").value;
    logpass.password = document.querySelector("input[name='psw']").value;
    let url = "https://kuchevskii.com/api/Login";
    xhttp.open("POST", url ,true);
    xhttp.setRequestHeader("content-type", "application/json");
    xhttp.onreadystatechange = respose;
    let str = JSON.stringify(logpass);
    xhttp.send(str);
}
function respose(){
    if(xhttp.readyState == 4 && xhttp.status == 200){
        console.log(xhttp.responseText);
        createCookie();
        window.location.replace("https://kuchevskii.com/user/cards.html");
    }
}
/*create cookie*/
function createCookie(){
    let name = "JWT";
    let value = JSON.parse(xhttp.responseText).token;
    document.cookie = name + '=' + value
       + "; path=/; domain=kuchevskii.com; secure";
}

/*
var ebachObj = {   
    "date":"1993-10-10",
    "team":"team",
    "code":"code",
    "result":"result",
    "sex":"MALE",
    "age":25,
    "fullName":"Full Name",
    "address":"address",
    "regularPatient": false,
    "homeless": true,
    "data":"data"
};

var ebachJSON = JSON.stringify(ebachObj);

function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
      "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
function uniResponse(){
    if(xhttp.readyState == 4 && xhttp.status == 200){
        console.log(xhttp.responseText);
    }
}
function addEbach(){
    let url = "https://kuchevskii.com:4782/api/Add";
    let cookieToken = getCookie("JWT");
    let token = JSON.parse(cookieToken).token;
    xhttp.open("POST", url ,true);
    xhttp.setRequestHeader("JWT", token);
    xhttp.setRequestHeader("content-type", "application/json");
    xhttp.onreadystatechange = uniResponse;
    xhttp.send(ebachJSON);
}
function getAll(){
    let url = "https://kuchevskii.com:4782/api/GetAll";
    let cookieToken = getCookie("JWT");
    let token = JSON.parse(cookieToken).token;
    xhttp.open("GET", url ,true);
    xhttp.setRequestHeader("JWT", token);
    xhttp.setRequestHeader("content-type", "application/json");
    xhttp.onreadystatechange = parseAll;
    xhttp.send();
}
var patientArray;
function parseAll(){
    if(xhttp.readyState == 4 && xhttp.status == 200){
        patientArray = JSON.parse(xhttp.responseText);
        return patientArray;
    }
}
*/