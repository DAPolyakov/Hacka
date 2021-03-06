package ru.yandexschool.hackathon.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import ru.yandexschool.hackathon.R
import ru.yandexschool.hackathon.Utils
import ru.yandexschool.hackathon.ui.main.MainActivity
import com.afollestad.materialdialogs.MaterialDialog



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
    }
}
