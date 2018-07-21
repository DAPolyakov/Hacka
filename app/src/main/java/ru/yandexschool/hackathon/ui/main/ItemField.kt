package ru.yandexschool.hackathon.ui.main

import android.support.annotation.DrawableRes
import ru.yandexschool.hackathon.R


abstract class ItemField(
        @DrawableRes val img: Int,
        val rating: Int,
        var hp: Int = 0
)

class SmallBug() : ItemField(R.drawable.bug_lite, 100)
class EvilBug() : ItemField(R.drawable.bug_hard, 300)
class Idea() : ItemField(R.drawable.ic_idea, -5000, 5)
class EmptyField() : ItemField(R.drawable.white, 0) {

}