let roleList = []; // глобальная переменная для хранения массива ролей

//вызов метода получения всех юзеров и заполнения таблицы
getAllUsers();

function getAllUsers() {
    $.getJSON("http://localhost:8080/admin/users", function (data) { // по ссылки получаем юзеров и добавляем их в дата
        let rows = '';
        $.each(data, function (key, user) {
            rows += createRows(user);
        });
        $('#tableAllUsers').append(rows);

        $.ajax({
            url: '/admin/authorities',
            method: 'GET',
            dataType: 'json',
            success: function (roles) {
                roleList = roles;
            }
        });
    });
}

function createRows(user) {

    let user_data = '<tr id=' + user.id + '>';
    user_data += '<td>' + user.id + '</td>';
    user_data += '<td>' + user.name + '</td>';
    user_data += '<td>' + user.email + '</td>';
    user_data += '<td>' + user.age + '</td>';
    user_data += '<td>' + user.gender + '</td>';
    user_data += '<td>';
    let roles = user.authorities;
    for (let role of roles) {
        user_data += role.name.replace('ROLE_', '') + ' ';
    }
    user_data += '</td>' +
        '<td>' + '<input id="btnEdit" value="Edit" type="button" ' +
        'class="btn-info btn edit-btn" data-toggle="modal" data-target="#editModal" ' +
        'data-id="' + user.id + '">' + '</td>' +
        '<td>' + '<input id="btnDelete" value="Delete" type="button" class="btn btn-danger del-btn" ' +
        'data-toggle="modal" data-target="#deleteModal" data-id=" ' + user.id + ' ">' + '</td>';
    user_data += '</tr>';

    return user_data;
}

function getUserRolesForEdit() {
    let selectedRoles = [];
    for (let i = 0; i < $('#editRole').val().length; i++) {
        let role = {};
        role.id = $('#editRole').val().at(i);
        if ($('#editRole').val().at(i) == 1) {
            role.name = "USER";
        } else if ($('#editRole').val().at(i) == 2) {
            role.name = "ADMIN";
        }
        selectedRoles.push(role);
    }
    return selectedRoles;
}

$(document).on('click', '.edit-btn', function () {
    const user_id = $(this).attr('data-id');
    console.log("editUserId: " + JSON.stringify(user_id));
    $.ajax({
        url: '/admin/' + user_id,
        method: 'GET',
        dataType: 'json',
        success: function (user) {
            $('#editId').val(user.id);
            $('#editName').val(user.name);
            $('#editGender').val(user.gender);
            $('#editAge').val(user.age);
            $('#editEmail').val(user.email);
            $('#editPassword').val(user.password);
            $('#editRole').val(user.roles);
        }
    });
});

$('#editButton').on('click', (e) => {
    e.preventDefault();

    let editUser = {
        id: $('#editId').val(),
        name: $('#editName').val(),
        age: $('#editAge').val(),
        gender: $('#editGender').val().join(),
        email: $('#editEmail').val(),
        password: $('#editPassword').val(),
        roles: getUserRolesForEdit()
    }
    console.log(JSON.stringify(editUser))

    $.ajax({
        url: 'http://localhost:8080/admin',
        method: 'PUT',
        dataType: 'json',
        data: JSON.stringify(editUser),
        contentType: 'application/json; charset=utf-8',
        success: (data) => {
            let newRow = createRows(data);
            console.log("newRow: " + newRow)
            $('#tableAllUsers').find('#' + editUser.id).replaceWith(newRow);
            $('#editModal').modal('hide');
            $('#admin-tab').tab('show');
        },
        error: () => {
            console.log("error editUser")
        }

    });
});

$(document).on('click', '.del-btn', function () {

    let user_id = $(this).attr('data-id');
    console.log("userId: " + JSON.stringify(user_id));

    $.ajax({
        url: '/admin/' + user_id,
        method: 'GET',
        dataType: 'json',
        success: function (user) {
            $('#delId').empty().val(user.id);
            $('#delName').empty().val(user.username);
            $('#delAge').empty().val(user.age);
            $('#delGender').empty().val(user.gender);
            $('#delEmail').empty().val(user.email);
            $('#delPassword').empty().val(user.password);
            $('#delRole').val(user.roles);
        }
    });
});

$('#deleteButton').on('click', (e) => {
    e.preventDefault();
    let userId = $('#delId').val();
    $.ajax({
        url: '/admin/' + userId,
        method: 'DELETE',
        success: function () {
            $('#' + userId).remove();
            $('#deleteModal').modal('hide');
            $('#admin-tab').tab('show');
        },
        error: () => {
            console.log("error delete user")
        }
    });
});

function getUserRolesForAdd() {
    let selectedRoles = [];
    for (let i = 0; i < $('#newRoles').val().length; i++) {
        let role = {};
        role.id = $('#newRoles').val().at(i);
        if ($('#newRoles').val().at(i) == 1) {
            role.name = "USER";
        } else if ($('#newRoles').val().at(i) == 2) {
            role.name = "ADMIN";
        }
        selectedRoles.push(role);
    }
    return selectedRoles;
}

$('.newUser').on('click', () => {

    $('#name').empty().val('')
    $('#age').empty().val('')
    $('#password').empty().val('')
    $('#email').empty().val('')
})

$("#addNewUserButton").on('click', () => {
    let newUser = {
        name: $('#name').val(),
        age: $('#age').val(),
        gender: $('#gender').val().join(),
        email: $('#email').val(),
        password: $('#password').val(),
        roles: getUserRolesForAdd()
    }

    console.log("User: " + JSON.stringify(newUser))

    $.ajax({
        url: 'http://localhost:8080/admin',
        method: 'POST',
        dataType: 'json',
        data: JSON.stringify(newUser),
        contentType: 'application/json; charset=utf-8',
        success: function () {
            alert("Пользователь добавлен")
            $('#tableAllUsers').empty();
            getAllUsers();
            $('#admin-tab').tab('show');
        },
        error: function () {
            alert('error addUser')
        }
    });
});