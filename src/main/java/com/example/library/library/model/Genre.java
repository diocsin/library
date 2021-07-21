package com.example.library.library.model;

public enum Genre {
    ACTION_AND_ADVENTURE(1, "Приключения"),
    ALTERNATE_HISTORY(2, "Альтернативная история"),
    CHILDRENS(3, "Детская литература"),
    CLASSIC(4, "Классика"),
    COMIC_BOOK(5, "Комедия"),
    DRAMA(6, "Драма"),
    FANTASY(7, "Фантастика"),
    HORROR(8, "Ужасы"),
    POETRY(9, "Поэзия"),
    THRILLER(10, "Триллер"),
    SCIENCE(11, "Научная литература"),
    HISTORY(12, "История"),
    BIOGRAPHY(13, "Биография");

    private final int index;
    private final String rusName;

    Genre(int index, String rusName) {
        this.index = index;
        this.rusName = rusName;
    }

    public String getRusName() {
        return rusName;
    }

    public int getIndex() {
        return index;
    }
}
