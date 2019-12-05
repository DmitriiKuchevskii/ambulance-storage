var xhttp = new XMLHttpRequest();
function login(){
    var logpass = {username:"", password:""};
    logpass.username = document.querySelector("input[name='uname']").value;
    logpass.password = document.querySelector("input[name='psw']").value;
    var url = "https://kuchevskii.com/api/Login";
    xhttp.open("POST", url ,true);
    xhttp.setRequestHeader("content-type", "application/json");
    xhttp.onreadystatechange = respose;
    var str = JSON.stringify(logpass);
    xhttp.send(str);
}
function respose(){
    if(xhttp.readyState == 4 && xhttp.status == 200){
        console.log(xhttp.responseText);
        createCookie();
    }
}
/*create cookie*/
function createCookie(){
    let name = "token";
    let value = xhttp.responseText;
    document.cookie = encodeURIComponent(name) + '=' + encodeURIComponent(value);
}
