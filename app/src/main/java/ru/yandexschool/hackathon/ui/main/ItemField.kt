package ru.yandexschool.hackathon.ui.main

import android.support.annotation.DrawableRes
import ru.yandexschool.hackathon.R


abstract class ItemField(
        @DrawableRes val img: Int
)


class SmallBag() : ItemField(R.drawable.bug) {

}

class EmptyField() : ItemField(R.drawable.ic_launcher_background) {

}