package ru.yandexschool.hackathon.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.yandexschool.hackathon.R
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val random = Random(System.currentTimeMillis())

    private var speed = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bagFieldRv.layoutManager = GridLayoutManager(this, 4)
        bagFieldRv.adapter = BagRvAdapter()

        startBugTimer()
    }

    private fun startBugTimer() {
        Observable
                .just(1)
                .delay(speed, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.wtf("bugbug", "new")
                    speed -= 300
                    speed = Math.max(speed, 300L)
                    (bagFieldRv?.adapter as? BagRvAdapter)?.addItemToField(SmallBag())
                    startBugTimer()
                }

    }

}
