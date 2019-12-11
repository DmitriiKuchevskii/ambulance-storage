/*Get data from server*/

/*get all patients data and create array of objects from recieved data*/
document.addEventListener("load", getAll);
function getAll(){
    let url = "https://kuchevskii.com:4782/api/GetAll";
    let cookieToken = getCookie("token");
    let token = JSON.parse(cookieToken).token;
    xhttp.open("GET", url ,true);
    xhttp.setRequestHeader("JWT", token);
    xhttp.setRequestHeader("content-type", "application/json");
    xhttp.onreadystatechange = parseAll;
    xhttp.send();
}
/*all patients data*/
var patientDataStr;
/*get patients data from cookies*/
function parseAll(){
    if(xhttp.readyState == 4 && xhttp.status == 200){
        patientDataStr = JSON.parse(xhttp.responseText);
        return patientDataStr;
    }
}
/*parse cookie*/
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
      "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

/*array of objects with patients data */
var patientArray = JSON.parse(patientDataStr);

/*fill the table with patients info*/
function fillTheTable(array){
    for(let i = 0; i < array.length; i++){
        let patientInfo = array[i];
        createTableLine(patientInfo);
    }
}

function createTableLine(patientObj){
    var table = document.getElementById('table');
    var tr = document.createElement('tr');
    table.append(tr);
    for(let i = 0; i < 8; i++){
        var trLast = table.lastChild;
        let td = document.createElement('td');
        trLast.appendChild(td);
        switch(i){
            case 0:
                trLast.lastChild.innerHTML = patientObj.date;
                break;
            case 1:
                trLast.lastChild.innerHTML = patientObj.team;
                break;
            case 2:
                trLast.lastChild.innerHTML = patientObj.code;
                break;
            case 3:
                trLast.lastChild.innerHTML = patientObj.result;
                break;
            case 4:
                trLast.lastChild.innerHTML = patientObj.id;
                break;            
            case 5:
                trLast.lastChild.innerHTML = patientObj.fullName;
                break; 
            case 6:
                trLast.lastChild.innerHTML = patientObj.address;
                break;
            case 7:
                trLast.lastChild.innerHTML = 'ответственный';
                break;
        }
    }
}

/*clear table*/
function clearTable(){
    var table = document.getElementById('table');
    var trCollection = table.getElementsByTagName('tr');
    var linesQuantity = trCollection.length;
    for(let i = 1; i < linesQuantity; i++){
        trCollection[1].remove();
    }
}

/*===============SEARCH===============*/
function search(){
    var word = document.getElementsByClassName("searchInput")[0].value;
    var result = findPatients(word);
    clearTable();
    fillTheTable(result);
}
function findPatients(inputValue){
    var select = document.getElementsByClassName("searchSelect")[0];
    var resultArray;
    var value = select.value;
    resultArray = patientArray.filter(filterCallback(value, inputValue));
    return resultArray;
}

function filterCallback(propName, propValue){
    return function(element){
        if(element[propName].toString() === propValue){
            return true;
        }
    }
}
/*
var patientArray = [
    {
        address: "улица Пушкина, дом Колотушкина",
        age: 52,
        code: "F20.60 -- Шизофрения, простой тип -- непрерывный тип течения",
        data: "data",
        date: "1967-04-30T00:00:00.000+0000",
        fullName: "Филипп Киркоров",
        homeless: false,
        id: 1388,
        regularPatient: true,
        result: "13 - Доставлен в больницу",
        sex: "CUSTOM",
        team: "123456789"
    },
    {
        address: "улица Колотушкина, дом Пушкина",
        age: 56,
        code: "F03.41 -- Психоз на фоне деменции неуточненной -- бредовые симптомы, сенильный",
        data: "data1",
        date: "1963-10-20T00:00:00.000+0000",
        fullName: "Соловьёв Владимир Рудольфович",
        homeless: false,
        id: 1390,
        regularPatient: true,
        result: "13 - Доставлен в больницу",
        sex: "MALE",
        team: "987654321"
    },
    {
        address: "Фабрика звёзд",
        age: 36,
        code: "F06.0 -- Органический галлюциноз",
        data: "data1",
        date: "1983-08-15T00:00:00.000+0000",
        fullName: "Тимати",
        homeless: false,
        id: 1391,
        regularPatient: true,
        result: "13 - Доставлен в больницу",
        sex: "MALE",
        team: "987654321"
    }
];
*/