function EditUser() {
    let form = window.formEditUser.editRoles;
    let new_Roles;
    let new_Id;

    let rolesList = document.createElement('ul');

    for (var i = 0; i < form.length; i++) {
        var option = form.options[i];
        let role = document.createElement('li');
        if (option.selected) {``
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

    let id = window.formEditUser.editID.value;


    fetch('http://localhost:8080/admin/users/' + id, {
        method: 'PUT',
        body: JSON.stringify({
            id: window.formEditUser.editID.value,
            name: window.formEditUser.editName.value,
            surname: window.formEditUser.editSurname.value,
            age: window.formEditUser.editAge.value,
            username: window.formEditUser.editUsername.value,
            password: window.formEditUser.editPassword.value,
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
                getModalEdit(id);
                alert(body.info);

            }
            return response.json()
        })
        .then(user => {
            $('#' + id).replaceWith('<tr id=' + id + '>' +
                '<td>' + id + '</td>' +
                '<td>' + window.formEditUser.editName.value + '</td>' +
                '<td>' + window.formEditUser.editSurname.value + '</td>' +
                '<td>' + window.formEditUser.editAge.value + '</td>' +
                '<td>' + window.formEditUser.editUsername.value + '</td>' +
                '<td>' + rolesList.textContent + '</td>' +
                '<td> <button type="button" onclick="getModalEdit(' + id + ')" class="btn btn-primary btn-sm">Edit</button> </td>' +
                '<td> <button type="button" onclick="getModalDelete(' + id + ')" class="btn btn-danger btn-sm">Delete</button> </td>' +
                '</tr>');
        });
}