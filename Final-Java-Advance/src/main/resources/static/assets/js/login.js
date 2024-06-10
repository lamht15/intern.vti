function openFormRegister() {
    document.getElementById("register").style.display = "block";
    document.getElementById("loginForm").style.display = "none";
}

function closeForm() {
    document.getElementById("register").style.display = "none";
    document.getElementById("loginForm").style.display = "block";
}

var api;

function loadDepartmentID(department_selected){

}
function createAccount(){
    api = "http://localhost:8080/api/v1/accounts/create";
    let registryForm = document.getElementById("registerForm");
    registryForm.addEventListener("submit", (e) => {
        e.preventDefault();
        var username = document.getElementById("username_register").value;
        var password = document.getElementById("password_register").value;

        var firstname = document.getElementById("firstName_register").value;
        var lastname = document.getElementById("lastName_register").value;

        var role = document.getElementById("role_register");
        var role_selected = role.options[role.selectedIndex].text;

        var departmentName = document.getElementById("departmentOptions_register");
        var department_selected = departmentName.options[departmentName.selectedIndex].text;

        //Lấy ID tương ứng
        var api_get = "http://localhost:8080/api/v1/departments/name?name=" + department_selected;
        fetch(api_get)
            .then((data) => data.json())
            .then((data) => {
            var id = data.id;

            //Post tạo tài khoản
            fetch(api, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password,
                    firstname: firstname,
                    lastname: lastname,
                    role: role_selected,
                    departmentID: id
                })
            })
        })
    });
}