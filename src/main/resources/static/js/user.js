document.addEventListener("DOMContentLoaded", function (event) {
    eventForUserPage();
    addUserButton()
});

function searchOnTable() {
    let searchText = document.querySelector("#search_table").value.trim();
    const param = new URLSearchParams({
        "filterText": searchText,
    });
    fetch("users/filter?" + param).then(response => response.text()).then(fragment => {
            document.querySelector(".user_list").innerHTML = fragment
            eventForUserPage()
        }
    )
}

function addUserButton() {
    document.getElementById('addBtn').addEventListener('click', function (event) {
        event.preventDefault();
        let href = this.getAttribute('href');
        fetch(href).then(response => response.text()).then(fragment => {
            document.querySelector("#addModal").innerHTML = fragment;
        }).then(() => {
            let model = new bootstrap.Modal(document.getElementById('addModal'), {});
            model.show();
            document.getElementById("add_user")
                .addEventListener('submit', event => submitNewUserForm(event))
        });
    });
}

function editEvent(el) {
    el.addEventListener('click', function (event) {
        event.preventDefault()
        let href = this.getAttribute('href')
        editAsyncFetch(href)
    })
}

function editEventRow(el) {
    el.addEventListener('dblclick', function (event) {
        event.preventDefault()
        let editBtn = el.querySelector('.editBtn')
        let href = editBtn.getAttribute('href')
        editAsyncFetch(href)
    })
}

function editAsyncFetch(href) {
    fetch(href).then(response => response.text()).then(fragment => {
        document.querySelector("#editModal").innerHTML = fragment;
    }).then(() => {
        let model = new bootstrap.Modal(document.getElementById('editModal'), {});
        model.show();
        document.getElementById("edit_user")
            .addEventListener('submit', event => submitEditUserForm(event))
    });
}

function eventForUserPage() {
    document.querySelectorAll('.table .editBtn')
        .forEach(editBtn => editEvent(editBtn));

    document.querySelectorAll('.table tr')
        .forEach(tr => editEventRow(tr));

    document.querySelectorAll('.table .deleteBtn')
        .forEach(deleteBtn => deleteBtn.addEventListener('click', function (event) {
            event.preventDefault();
            let href = this.getAttribute('href');
            document.querySelector('#deleteModal .modal-footer a').setAttribute('href', href)
            let model = new bootstrap.Modal(document.getElementById('deleteModal'), {});
            model.show();
            let delUser = document.getElementById('delUser');
            delUser.addEventListener('click', ev => deleteUser(ev))
        }))
}

async function deleteUser(event) {
    event.preventDefault();
    let el = event.target;
    let href = el.getAttribute('href');
    fetch(href).then(response => response.text()).then(fragment => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'))
        modal.hide();
        document.querySelector(".user_list").innerHTML = fragment;
        eventForUserPage();
    })
}

async function submitNewUserForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData
        });
    const param = new URLSearchParams({
        "login": formData.get('login'),
    });
    fetch("users/checkLogin?" + param).then(response => {
            if (response.ok) {
                saveUser(request)
            } else {
                return Promise.reject(response);
            }
        }
    ).catch(error => error.text()).then(errorFr => {
        document.querySelector("#addModal .custom-alert").innerHTML = errorFr;
    });
}

function saveUser(request) {
    fetch(request).then(response => response.text()).then(fr => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('addModal'))
        modal.hide();
        document.querySelector(".user_list").innerHTML = fr;
        eventForUserPage();
    });
}

async function submitEditUserForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData
        });
    let response = await fetch(request);
    let userTable = await response.text();
    console.dir(userTable)

    let modal = bootstrap.Modal.getInstance(document.getElementById('editModal'))
    modal.hide();
    document.querySelector(".user_list").innerHTML = userTable;
    eventForUserPage();
}