function about() {
    console.dir($(this));
    $.get("/about").done(fragment => {
        $('main').replaceWith(fragment);
    });
}