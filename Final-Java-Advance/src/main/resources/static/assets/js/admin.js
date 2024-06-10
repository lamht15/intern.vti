var currentPage;
var maxPage;
var api;
var nextPageNum;
var previousPageNum;
//Load Department Name
function loadDepartmentName(){
    api = "http://localhost:8080/api/v1/departments/filter";
    str2 = ""
    fetch(api)
        .then((data) => data.json())//Lấy data và convert sang json
        .then((data) => {
            currentPage = data.pageable.pageNumber;
            maxPage = data.totalPages;
            data.content.forEach((item) => {
            str2 += `
                <tr>
                    <option>${item.name}</option>
            `;
            document.querySelector("#departmentDropdown").innerHTML = str2;
            document.querySelector("#departmentOptions").innerHTML = str2;
            document.querySelector("#departmentOptions_update").innerHTML = str2;
            });
        });
}

//Display Pages All Accounts
function displayAccountList(page) {
    document.getElementById("departmentContent").style.display = "none";
    loadDepartmentName();
    var content = document.getElementById("content");
    content.style.display = "block";


    api = "http://localhost:8080/api/v1/accounts/filter?page=" + page;
    displayAllAccounts(api);

    currentPage = page;

    var currentPageNumber = document.getElementById("currentPageNum");
    if(currentPageNumber){
        currentPageNumber.innerText = "Page " + (currentPage + 1);
    }
}

function previousPage(){
    displayAccountList(previousPageNum);
}

function nextPage(){
    displayAccountList(nextPageNum);
}

//Search By Username
function searchByUsername(){
    var username = document.getElementById("search-input").value;
    api = "http://localhost:8080/api/v1/accounts/filter?search=" + username;
    displayAllAccounts(api);
}

//Filter By ROLE and Department
function submitFilter(){
    var role = document.getElementById("roleDropdown");
    var role_selected = role.options[role.selectedIndex].text;

    var department = document.getElementById("departmentDropdown");
    var department_selected = department.options[department.selectedIndex].text;
    api = "http://localhost:8080/api/v1/accounts/filter?departmentName=" + department_selected + "&role=" + role_selected;
    displayAllAccounts(api);
}

//Hiện từng Account
function displayAccounts(api){
    let str1 = "";
    fetch(api)
        .then((data) => data.json())
        .then((data) => {
            str1 += `
                <tr>
                    <td>Unknown</td>
                    <td>${data.username}</td>
                    <td>${data.firstname}  ${data.lastname}</td>
                    <td>${data.role}</td>
                    <td>${data.department.name}</td>
                    <td>
                        <button onclick="editAccount()">
                           <img th:src="@{/assets/images/edit.png}" src="../static/assets/images/edit.png" class = "custom">
                        </button>
                        <button onclick="deleteAccount()">
                           <img th:src="@{/assets/images/delete-acc.png}" src="../static/assets/images/delete-acc.png" class = "custom">
                        </button>
                    </td>
                </tr>
                `
            document.querySelector("#tbody").innerHTML = str1;
        });

}

//Hiện hết Account
function displayAllAccounts(api){
    let str = "";
    fetch(api)
        .then((data) => data.json())//Lấy data và convert sang json
        .then((data) => {
            maxPage = data.totalPages;
            console.log(data);
            data.content.forEach((item) => {
            if (item.department && item.department.name) {
            str += `
                <tr>
                    <td><input type = "checkbox" class = "checkbox"></td>
                    <td>${item.username}</td>
                    <td>${item.firstname}  ${item.lastname}</td>
                    <td>${item.role}</td>
                    <td>${item.department.name}</td>
                    <td>
                        <button onclick="openUpdate(this)">
                           Edit
                        </button>
                        <button onclick="deleteUser(this)">
                           Delete
                        </button>
                    </td>
                </tr>
            `;
            document.querySelector("#tbody").innerHTML = str;
            } else {
            str += `
                <tr>
                    <td><input type = "checkbox" class = "checkbox"></td>
                    <td>${item.username}</td>
                    <td>${item.firstname}  ${item.lastname}</td>
                    <td>${item.role}</td>
                    <td>N/A</td>
                    <td>
                        <button onclick="openUpdate(this)">
                           Edit
                        </button>
                        <button onclick="deleteUser(this)">
                           Delete
                        </button>
                    </td>
                </tr>
            `;
            document.querySelector("#tbody").innerHTML = str;
            }
                if(currentPage - 1 >= 0){
                    previousPageNum = currentPage - 1;
                }
                else{
                    previousPageNum = maxPage - 1;
                }

                if(currentPage + 1 <= maxPage - 1){
                    nextPageNum = currentPage + 1;
                }
                else{
                    nextPageNum = 0;
                }

            });
        });
}

//Đóng mở Form tạo Account
function submitAdd(){
    document.getElementById("registerFormOverlay").style.display = "block";
}

function closeForm() {
    document.getElementById("registerFormOverlay").style.display = "none";
}

//Tạo Account
function createAccount(){
    var username;
    var firstname;
    var lastname;
    var role_selected;
    var department_selected;

    let registryForm = document.getElementById("registerForm");

    registryForm.addEventListener("submit", (e) => {
      e.preventDefault();
        username = document.getElementById("username").value;
        firstname = document.getElementById("firstName").value;
        lastname = document.getElementById("lastName").value;
        var role = document.getElementById("role");
        role_selected = role.options[role.selectedIndex].text;
        var departmentName = document.getElementById("departmentOptions");
        department_selected = departmentName.options[departmentName.selectedIndex].text;

//        console.log(department_selected);//

        //Lấy Department ID tương ứng
        api = "http://localhost:8080/api/v1/departments/name?name=" + department_selected;
        fetch(api)
            .then((data) => data.json())
            .then((data) => {
                console.log(data);
                var departmentID = data.id;
                console.log(departmentID);

                //////////////////////////////////////////////////
                        api = "http://localhost:8080/api/v1/accounts/create";
                            fetch(api, {
                                method: 'POST',
                                headers: {
                                'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({
                                   username: username,
                                   password: 123456,
                                   firstname: firstname,
                                   lastname: lastname,
                                   role: role_selected,
                                   departmentID: departmentID
                                })
                            })
                //            .then(response => response.json())
                            .then(data => console.log('Success', data))
                            .catch((error) => console.log('Error:', error));
                        closeForm();
                //////////////////////////////////////////////////

            });
    });

};

//Refresh
function submitRefresh(){
    location.reload();
}

function submitDelete(){
  var selectedUsers = [];

  var checkboxes = document.getElementsByClassName("checkbox");
  var usernames = document.querySelectorAll('#userTable td:nth-child(2)'); // Select all username cells

  for (var i = 0; i < checkboxes.length; i++) {
    if (checkboxes[i].checked) {
      var username = usernames[i].textContent.trim(); // Get the text content of the corresponding username cell
      selectedUsers.push(username);
    }
  }
  console.log(selectedUsers);
  selectedUsers.forEach(selected_username => {
    api = "http://localhost:8080/api/v1/accounts/delete/username?username=" + selected_username;
    fetch(api, {
        method: "DELETE"
        });
    });
}

function deleteUser(button){
    var row = button.parentNode.parentNode; // Get the parent row of the button
    var username = row.cells[1].innerText; // Get the username from the second cell of the row
//    alert("Username to be deleted: " + username);
    api = "http://localhost:8080/api/v1/accounts/delete/username?username=" + username;
    fetch(api, {
        method: "DELETE"
    });
}

//////////////
function openUpdate(button){
    document.getElementById("updateFormOverlay").style.display = "block";
    var row = button.parentNode.parentNode; // Get the parent row of the button
    var username = row.cells[1].innerText; // Get the username from the second cell of the row
    var fullName = row.cells[2].innerText;

    var updateForm = document.getElementById("updateForm");

    document.getElementById("username_update").value = username;

    let words = fullName.split(" ");
    document.getElementById("firstName_update").value = words[0];
    document.getElementById("lastName_update").value = words.slice(1).join(" ");

}

function closeUpdateForm() {
    document.getElementById("updateFormOverlay").style.display = "none";
}

function updateAccount(){
    var username;
    var firstname;
    var lastname;
    var role_selected;
    var department_selected;

    let loginForm = document.getElementById("updateForm");

    loginForm.addEventListener("submit", (e) => {
      e.preventDefault();
        username = document.getElementById("username_update").value;
        firstname = document.getElementById("firstName_update").value;
        lastname = document.getElementById("lastName_update").value;
        var role = document.getElementById("role_update");
        role_selected = role.options[role.selectedIndex].text;
        var departmentName = document.getElementById("departmentOptions_update");
        department_selected = departmentName.options[departmentName.selectedIndex].text;

//        console.log(department_selected);//

        //Lấy Department ID tương ứng
        api = "http://localhost:8080/api/v1/departments/name?name=" + department_selected;
        fetch(api)
            .then((data) => data.json())
            .then((data) => {
                console.log(data);
                var departmentID = data.id;
                console.log(departmentID);

            //Lấy Account ID
            api = "http://localhost:8080/api/v1/accounts/username?username=" + username;
            fetch(api)
                .then((data) => data.json())
                .then((data) => {
                    var accountID = data.id;
                    var password = data.password;

                //////////////////////////////////////////////////
                        api = "http://localhost:8080/api/v1/accounts/update?id=" + accountID;
                            fetch(api, {
                                method: 'PUT',
                                headers: {
                                'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({
//                                   id : accountID,
                                   username: username,
                                   firstname: firstname,
                                   password: password,
                                   lastname: lastname,
                                   role: role_selected,
                                   departmentID: departmentID
                                })
                            })
                //            .then(response => response.json())
                            .then(data => console.log('Success', data))
                            .catch((error) => console.log('Error:', error));
                        closeForm();
                //////////////////////////////////////////////////
                });

            });
    });
}

//DEPARTMENTS
function displayDepartmentList(page){
    var content1 = document.getElementById("content");
    content1.style.display = "none";
    var content = document.getElementById("departmentContent");
    content.style.display = "block";
    api = "http://localhost:8080/api/v1/departments/filter?page=" + page;
    displayAllDepartments(api);

    currentPage = page;

    var currentPageNumber = document.getElementById("currentPageNum2");
    if(currentPageNumber){
        currentPageNumber.innerText = "Page " + (currentPage + 1);
    }
}

function displayAllDepartments(api){
    var str3 = "";
    fetch(api)
        .then(data => data.json())
        .then(data => {
            maxPage = data.totalPages;
            data.content.forEach((item) => {
            console.log(data);
                str3 += `
                <tr>
                    <td><input type = "checkbox" class = "checkbox2"></td>
                    <td>${item.name}</td>
                    <td>${item.total_member}</td>
                    <td>${item.type}</td>
                    <td>${item.createdDate}</td>
                    <td>
                            <button onclick="openUpdateDepartmentForm(this)">
                               Edit
                            </button>
                            <button onclick="deleteDepartment(this)">
                               Delete
                            </button>
                            <button onclick="addAccount(this)">
                               Add
                            </button>
                    </td>
                </tr>
                `;
                document.querySelector("#departmentBody").innerHTML = str3;
                    if(currentPage - 1 >= 0){
                        previousPageNum = currentPage - 1;
                    }
                    else{
                        previousPageNum = maxPage - 1;
                    }

                    if(currentPage + 1 <= maxPage - 1){
                        nextPageNum = currentPage + 1;
                    }
                    else{
                        nextPageNum = 0;
                    }
            });
        });
}

function searchByDepartmentName(){
    var name = document.getElementById("search-departmentName").value;
    api = "http://localhost:8080/api/v1/departments/filter?search=" + name;
    displayAllDepartments(api);
}

function previousPage2(){
    displayDepartmentList(previousPageNum);
}

function nextPage2(){
    displayDepartmentList(nextPageNum);
}

function filterDepartment(){
    var type = document.getElementById("typeList");
    var type_selected = type.options[type.selectedIndex].text;

    var min = document.getElementById("minDate").value;
    var max = document.getElementById("maxDate").value;


    api = "http://localhost:8080/api/v1/departments/filter?minCreatedDate=" + min + "&maxCreatedDate=" + max + "&type=" + type_selected;
    displayAllDepartments(api);
}

function submitAddDepartment(){
    document.getElementById("addDepartmentFormOverlay").style.display = "block";
}

function closeAddDepartmentForm(){
    document.getElementById("addDepartmentFormOverlay").style.display = "none";
}

function createDepartment(){
    var name;
    var type_selected;

    let addForm = document.getElementById("addDepartmentForm");
    addForm.addEventListener("submit", (e) => {
        e.preventDefault();
        name = document.getElementById("departmentName").value;

        var type = document.getElementById("type");
        type_selected = type.options[type.selectedIndex].text;
        console.log(name + "-" + type_selected);
        api = "http://localhost:8080/api/v1/departments/create";
        fetch(api, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: name,
                type: type_selected,
            })
        })
            closeAddDepartmentForm();
    });
}

function submitDeleteDepartment(){
  var selectedDepartments = [];

  var checkboxes = document.getElementsByClassName("checkbox2");
  var departments = document.querySelectorAll('#departmentTable td:nth-child(2)'); // Select all username cells

  for (var i = 0; i < checkboxes.length; i++) {
    if (checkboxes[i].checked) {
      var departmentName = departments[i].textContent.trim(); // Get the text content of the corresponding username cell
      selectedDepartments.push(departmentName);
    }
  }
  console.log(selectedDepartments);
  selectedDepartments.forEach(selected_department => {
    api = "http://localhost:8080/api/v1/departments/delete?name=" + selected_department;
    fetch(api, {
        method: "DELETE"
        });
    });
}

function openUpdateDepartmentForm(button){
    document.getElementById("updateDepartmentFormOverlay").style.display = "block";
    var row = button.parentNode.parentNode; // Get the parent row of the button
    var name = row.cells[1].innerText; // Get the username from the second cell of the row
    document.getElementById("departmentName_update").value = name;
}

function closeUpdateDepartmentForm(){
    document.getElementById("updateDepartmentFormOverlay").style.display = "none";
}

function updateDepartment(){
    var name;
    var type_selected;
    var id;
    let updateForm = document.getElementById("updateDepartmentForm");
    updateForm.addEventListener("submit", (e) => {
        name = document.getElementById("departmentName_update").value;

        var type = document.getElementById("type_update");
        type_selected = type.options[type.selectedIndex].text;

        total_member = document.getElementById("total_member_update") .value;

        api = "http://localhost:8080/api/v1/departments/name?name=" + name;
        fetch(api)
            .then(data => data.json())
            .then(data => {
                id = data.id;
                console.log(id);
                api = "http://localhost:8080/api/v1/departments/update?id=" + id;
                fetch(api, {
                    method: "PUT",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        name: name,
                        type: type_selected,
                    })
                });
            });
    });
    closeUpdateDepartmentForm();
}

function deleteDepartment(button){
    var row = button.parentNode.parentNode; // Get the parent row of the button
    var name = row.cells[1].innerText; // Get the username from the second cell of the row
    api = "http://localhost:8080/api/v1/departments/delete?name=" + name;
    fetch(api, {
        method: "DELETE"
    });
}