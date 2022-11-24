function newUser() {
    let form = window.formNewUser.newRoles;
    let addUserForm = $('#formNewUser')
    let new_Roles;
    let new_Id;
    let rolesList = document.createElement('ul');

    for (var i = 0; i < form.length; i++) {
        var option = form.options[i];
        let role = document.createElement('li');
        if (option.selected) {
            if (option.value === "ROLE_ADMIN") {
                new_Roles = option.value;
                new_Id = 1;

            } else {
                new_Roles = option.value;
                new_Id = 2;

            }


            role.textContent = option.value.substring(5, 10) + " ";
            rolesList.appendChild(role);
        }
    }


    fetch('http://localhost:8080/admin/users', {
        method: 'POST',
        body: JSON.stringify({
            name: window.formNewUser.newName.value,
            surname: window.formNewUser.newSurname.value,
            age: window.formNewUser.newAge.value,
            username: window.formNewUser.newUsername.value,
            password: window.formNewUser.newPassword.value,

            roles:
                [
                    {
                        "id": new_Id,
                        "roleName": new_Roles
                    }
                ]

        }),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    })

        .then(async (response) => {
            if (!response.ok) {
                let body = await response.json();
                let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                           ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
                addUserForm.prepend(alert);

            }
            return response.json()
        })
        .catch((error) => console.log(error.message))
        .then(user => {

            $('#tBody tr:last').after('<tr id=' + user.id + '>' +
                '<td>' + user.id + '</td>' +
                '<td>' + window.formNewUser.newName.value + '</td>' +
                '<td>' + window.formNewUser.newSurname.value + '</td>' +
                '<td>' + window.formNewUser.newAge.value + '</td>' +
                '<td>' + window.formNewUser.newUsername.value + '</td>' +
                '<td>' + rolesList.textContent + '</td>' +
                '<td> <button type="button" onclick="getModalEdit(' + user.id + ')" class="btn btn-primary btn-sm">Edit</button> </td>' +
                '<td> <button type="button" onclick="getModalDelete(' + user.id + ')" class="btn btn-danger btn-sm">Delete</button> </td>' +
                '</tr>');

            window.formNewUser.newName.value = "";
            window.formNewUser.newSurname.value = "";
            window.formNewUser.newAge.value = "";
            window.formNewUser.newUsername.value = "";
            window.formNewUser.newPassword.value = "";
            window.formNewUser.newRoles.value = "";


            $('.nav-tabs a[href="#users_table"]').tab('show');

        })




}



