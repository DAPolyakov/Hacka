package ru.yandexschool.hackathon.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.yandexschool.hackathon.R
import ru.yandexschool.hackathon.entity.Rating
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var gameIsFinished = false

    val random = Random(System.currentTimeMillis())
    private var speed = 2000L
    private var score = 0
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bagFieldRv.layoutManager = GridLayoutManager(this, 4)
        bagFieldRv.adapter = BagRvAdapter(object : BagRvListener {
            override fun finishGame() {
                this@MainActivity.finishGame()
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
        MaterialDialog.Builder(this)
                .title("Your score is $score")
                .input(null, null, MaterialDialog.InputCallback { dialog, input ->
                    // Publish
                    val rating = Rating(databaseReference.push().key!!,1, input.toString(), score)
                    databaseReference.child(rating.id).setValue(rating)
                    finish()
                }).show()
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

                        val item = if (r < 80) SmallBag() else EvilBag()

                        (bagFieldRv?.adapter as? BagRvAdapter)?.addItemToField(item)

                        startBugTimer()
                    }
        }
    }

}
