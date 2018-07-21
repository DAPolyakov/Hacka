package ru.yandexschool.hackathon.utils

enum class Progers {
    WORKING,
    SLEEP,
    GAME,
    ABSENT
}

enum class Items (val desc: String) {

    COFFEE("Кофе"),
    CAT("Котик"),
    PUNCH("Волшебный пендель"),
    PROGER("Программист");

    override fun toString(): String {
        return desc
    }
}