package ru.yandexschool.hackathon.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import ru.yandexschool.hackathon.R
import ru.yandexschool.hackathon.Utils
import ru.yandexschool.hackathon.ui.main.MainActivity


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Utils.setUpStatusBarColor(window, ResourcesCompat.getColor(getResources(), android.R.color.white, null))
    }

    override fun onResume() {
        super.onResume()
        activity_start_btn_start.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        activity_start_btn_rating.setOnClickListener {
            startActivity(Intent(this, RatingActivity::class.java))
        }

//        val score = 10
//        MaterialDialog.Builder(this)
//                .title("Your score is $score")
//                .input(null, null, MaterialDialog.InputCallback { dialog, input ->
//                    // Publish
//                }).show()
    }
}
