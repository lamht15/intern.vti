function openFormRegister(username) {
    document.getElementById("update").style.display = "block";
    document.getElementById("welcome-content").style.display = "none";
    document.getElementById("username_const").value = username;

    fetch("http://localhost:8080/api/v1/accounts/username?username=" + username)
        .then(data => data.json())
        .then(data => {
            console.log(data);
            document.getElementById("role").value = data.role;
            document.getElementById("firstName").value = data.firstname;
            document.getElementById("lastName").value = data.lastname;
            fetch("http://localhost:8080/api/v1/departments/"+data.department.id)
                    .then(data => data.json())
                    .then(data => {
                    document.getElementById("departmentOptions").value = data.name;
            });
        });
}

function closeForm() {
    document.getElementById("update").style.display = "none";
    document.getElementById("welcome-content").style.display = "block";
}

function updateAccount(){
    let updateForm = document.getElementById("updateForm");

    var api;
    updateForm.addEventListener("submit", (e) => {
        e.preventDefault();
        var username = document.getElementById("username_const").value;
        var password = document.getElementById("password").value;
        var firstname = document.getElementById("firstName").value;
        var lastname = document.getElementById("lastName").value;

        var role = document.getElementById("role");
        var role_selected = role.options[role.selectedIndex].text;

        var departmentName = document.getElementById("departmentOptions");
        var department_selected = departmentName.options[departmentName.selectedIndex].text;

        api = "http://localhost:8080/api/v1/accounts/username?username=" + username;
        fetch(api)
            .then(data => data.json())
            .then(data => {
                var accountID = data.id;
                api = "http://localhost:8080/api/v1/departments/name?name=" + department_selected;
                fetch(api)
                    .then(data => data.json())
                    .then(data => {
                        var departmentID = data.id;
                        api = "http://localhost:8080/api/v1/accounts/update?id=" + accountID;
                        fetch(api, {
                            method: "PUT",
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                username: username,
                                password: password,
                                firstname: firstname,
                                lastname: lastname,
                                role: role_selected,
                                departmentID: departmentID
                            })
                        })
                        .then(data => alert("UPDATE SUCCESSFULLY!"))
                        .catch((error) => console.log('Error:', error));
                    });
            });
    });
}
