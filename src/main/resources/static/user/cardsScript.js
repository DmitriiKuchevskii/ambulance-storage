/*get all patients data, create array of objects, fill the html table*/
document.getElementsByClassName('btn')[1].addEventListener("click", function(){getAll(fillTheTable);});

function getAll(cFunc){
    let url = "https://kuchevskii.com/api/GetAllPatients";
    let token = getCookie("JWT");
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", url ,true);
    xhttp.setRequestHeader("JWT", token);
    xhttp.setRequestHeader("content-type", "application/json");
    xhttp.onreadystatechange = function(){
        clearTable();
        if(xhttp.readyState == 4 && xhttp.status == 200){
           cFunc(this); /*cFunc is callback function, this key word is refers to XMLHttpRequest object*/
        }
    };
    xhttp.send();
}
/*parse cookie*/
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
      "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
/*fill the table with all patients info*/
function fillTheTable(xhttp){
    var patientArray = JSON.parse(xhttp.responseText);
    for(let i = 0; i < patientArray.length; i++){
        let patientInfo = patientArray[i];
        createTableLine(patientInfo);
    }
}
function createTableLine(patientObj){
    let table = document.getElementById('table');
    let tr = document.createElement('tr');
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
                let deleteBtn = document.createElement('button');
                deleteBtn.type = 'button';
                deleteBtn.className = 'deleteBtn'
                let iconDel = document.createElement('i');
                iconDel.className = "fas fa-trash-alt";
                deleteBtn.append(iconDel);
                tr.lastChild.append(deleteBtn);
                let editBtn = document.createElement('button');
                editBtn.type = 'button';
                editBtn.className = 'editBtn';
                let iconEdit = document.createElement('i');
                iconEdit.className = "fas fa-edit";
                editBtn.append(iconEdit);
                tr.lastChild.append(editBtn);
                break;
        }
    }
}
function deletePatient(){
    let xhttp = new XMLHttpRequest();
    let url = "https://kuchevskii.com/api/RemovePatient";
    let token = getCookie("JWT");
    xhttp.open("POST", url ,true);
    xhttp.setRequestHeader("JWT", token);
    xhttp.setRequestHeader("content-type", "application/json");
    xhttp.setRequestHeader("id", 1402);
    xhttp.onreadystatechange = function(){
        console.log(xhttp.status);
        if(xhttp.readyState == 4 && xhttp.status == 200){
            alert(xhttp.responseText);
        }
    };
    xhttp.send({"id":1402});
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
document.getElementsByClassName('btn')[2].addEventListener("click", function(){getAll(search);});
function search(xhttp){
    var patientArray = JSON.parse(xhttp.responseText);
    var word = document.getElementsByClassName("searchInput")[0].value;
    var result = findPatients(word, patientArray);
    for(let i = 0; i < result.length; i++){
        let patientInfo = result[i];
        createTableLine(patientInfo);
    }
}
function findPatients(inputValue, array){
    var select = document.getElementsByClassName("searchSelect")[0];
    var resultArray;
    var value = select.value;
    resultArray = array.filter(filterCallback(value, inputValue));
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