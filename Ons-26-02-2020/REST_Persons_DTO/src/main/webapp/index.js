fetchFunction("http://localhost:8080/jpareststarter/api/person/all", insertAllUsersInTable);

function fetchFunction(fetchUrl, callback) {
    fetch(fetchUrl)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            callback(data);
        });
};

function insertAllUsersInTable(dataArray) {
    let printString = createTableFromArray(dataArray);
    document.getElementById("myDiv").innerHTML = printString;
};

function createTableFromArray(array) {
    let tableHead = "<tr><th>ID</th>" + "<th>First Name</th>" + "<th>Last Name</th>" + "<th>Phone</th>";
    let htmlRows = "";
    console.log(array.all);

    array.all.forEach(element => {
        let temp = "<tr>" +
            "<td>" + element.id + "</td>" +
            "<td>" + element.fName + "</td>" +
            "<td>" + element.lName + "</td>" +
            "<td>" + element.phone + "</td>" +
            "<tr>"
        htmlRows += temp;
    });

    return "<table border='1'>" + tableHead + htmlRows + "</table>";
};

function reloadUsers(dataArray) {
    let printString = createTableFromArray(dataArray);
    document.getElementById("myDiv").innerHTML = printString;
}

document.getElementById("rldBtn").addEventListener('click', (event) => {
    fetchFunction("http://localhost:8080/jpareststarter/api/person/all", insertAllUsersInTable);
});

document.getElementById("addBtn").addEventListener('click', (event) => {
    addUser();
});

function addUser() {
    let fNameText = document.getElementById("fNameText").value;
    let lNameText = document.getElementById("lNameText").value;
    let phoneText = document.getElementById("phoneText").value;

    let options = {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            fName: fNameText,
            lName: lNameText,
            phone: phoneText
        })
    }

    fetch("http://localhost:8080/jpareststarter/api/person/add", options);
}