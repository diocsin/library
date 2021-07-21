let domResolve;
let domReady = new Promise(function (resolve) {
    domResolve = resolve;
});


document.addEventListener("DOMContentLoaded", domResolve);

domReady.then(tab).then(clickOnFirstTab).then(tabBook);

function tab() {
    return new Promise(resolve => {

        const triggerTabList = document.querySelectorAll('#referencesTab a')
        triggerTabList.forEach(function (triggerEl) {
            const tabTrigger = new bootstrap.Tab(triggerEl);
            triggerEl.addEventListener('click', function (event) {
                event.preventDefault();
                const loadUrl = this.getAttribute('href'),
                    tabTarget = document.querySelector(this.getAttribute('data-bs-target'));
                deleteActive();
                tabTarget.classList.add('active');
                fetch(loadUrl).then(response => response.text()).then(fragment => {
                    tabTarget.innerHTML = fragment;
                })
                tabTrigger.show();
                return false;
            })
        })
        resolve();
    })
}

function deleteActive() {
    document.querySelectorAll('.tab-pane').forEach(tb => {
        if (tb.classList.contains('active')) {
            tb.classList.remove('active')
        }
    })
}

function clickOnFirstTab() {
    const authorTab = document.querySelector('#author-tab');
    eventFire(authorTab, 'click');
}

function eventFire(el, etype) {
    if (el.fireEvent) {
        el.fireEvent('on' + etype);
    } else {
        var evObj = document.createEvent('Events');
        evObj.initEvent(etype, true, false);
        el.dispatchEvent(evObj);
    }
}


function tabBook(){
    document.getElementById("bookTab").addEventListener("DOMNodeInserted",function(){
        eventForBookPage();
        addBookButton();
    });
}

function searchOnTable() {
    let searchText = document.querySelector("#search_table").value.trim();
    const param = new URLSearchParams({
        "filterText": searchText,
    });
    fetch("books/filter?" + param).then(response => response.text()).then(fragment => {
            document.querySelector(".book_list").innerHTML = fragment
            eventForBookPage()
        }
    )
}

function addBookButton() {
    document.getElementById('addBtn').addEventListener('click', function (event) {
        event.preventDefault();
        let href = this.getAttribute('href');
        fetch(href).then(response => response.text()).then(fragment => {
            document.querySelector("#addModal").innerHTML = fragment;
        }).then(() => {
            let model = new bootstrap.Modal(document.getElementById('addModal'), {});
            model.show();
            document.getElementById("add_book")
                .addEventListener('submit', event => submitNewBookForm(event))
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
        document.getElementById("edit_book")
            .addEventListener('submit', event => submitEditBookForm(event))
    });
}

function eventForBookPage() {
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
            let delBook = document.getElementById('delBook');
            delBook.addEventListener('click', ev => deleteBook(ev))
        }))
}

async function deleteBook(event) {
    event.preventDefault();
    let el = event.target;
    let href = el.getAttribute('href');
    fetch(href).then(response => response.text()).then(fragment => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'))
        modal.hide();
        document.querySelector(".book_list").innerHTML = fragment;
        eventForBookPage();
    })
}

async function submitNewBookForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData
        });
    saveBook(request)
}

function saveBook(request) {
    fetch(request).then(response => response.text()).then(fr => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('addModal'))
        modal.hide();
        document.querySelector(".book_list").innerHTML = fr;
        eventForBookPage();
    });
}

async function submitEditBookForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData
        });
    let response = await fetch(request);
    let bookTable = await response.text();

    let modal = bootstrap.Modal.getInstance(document.getElementById('editModal'))
    modal.hide();
    document.querySelector(".book_list").innerHTML = bookTable;
    eventForBookPage();
}