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

    var gameIsFinished = false

    val random = Random(System.currentTimeMillis())
    private var speed = 2000L

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bagFieldRv.layoutManager = GridLayoutManager(this, 4)
        bagFieldRv.adapter = BugRvAdapter(object : BugRvListener {
            override fun finishGame() {
                this@MainActivity.finishGame()
            }

            override fun addRating(rating: Int) {
                score += rating
                score = Math.max(0, score)
                updateScore()
            }
        })

        updateScore()
        startBugTimer()
    }

    private fun updateScore() {
        val s = "Score: $score ฿"
        tvScore.text = s
    }

    private fun finishGame() {
        gameIsFinished = true
    }

    private fun startBugTimer() {
        if (!gameIsFinished) {
            Observable
                    .just(1)
                    .delay(speed, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.wtf("bugbug", "new")
                        speed -= 300
                        speed = Math.max(speed, 300L)

                        val r = Math.abs(random.nextInt() % 100)

                        val item = when {
                            r < 70 -> SmallBug()
                            (r >= 70) && (r < 90) -> EvilBug()
                            else -> Idea()
                        }

                        (bagFieldRv?.adapter as? BugRvAdapter)?.addItemToField(item)

                        startBugTimer()
                    }
        }
    }

}
