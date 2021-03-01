$('document').ready(function () {
    eventTable();
});

function updateBookList(page, size) {
    $.get("pagination", {"page": page, size: size}).done(fragment => {
        $(".book_list").replaceWith(fragment);
        eventTable();
    });
}

function eventTable() {
    $('.table #editButton').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, (book, status) => {
            console.dir(book);
            $('#edit-book-id').val(book.id);
            $('#edit-book-name').val(book.name);
            $('#edit-book-genre').val(book.genre);
            $('#edit-book-author').val(book.author.id);
            $('#edit-book-year').val(book.dateOfIssue);
            $('#edit-book-countSheets').val(book.countOfSheets);
            $('#edit-book-count').val(book.count);
        });
        $('#editModal').modal();
    });
    $('.table #deleteButton').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#deleteModal #delRef').attr('href', href);
        $('#deleteModal').modal();
    });
    $('#searchBtn').click(() => {
        const url = '/books/search',
            searchText = $('#search').val();
        $.ajax({
            url: url,
            type: 'POST',
            data: {
                'search': searchText
            },
            success: data => {
                $(".book_list").html(data);
                eventTable();
            }
        })
    });
}

