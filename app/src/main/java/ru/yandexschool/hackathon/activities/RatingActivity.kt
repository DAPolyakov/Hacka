package ru.yandexschool.hackathon.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_rating.*
import ru.yandexschool.hackathon.R
import ru.yandexschool.hackathon.Utils
import ru.yandexschool.hackathon.adapters.RatingRecyclerAdapter

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        Utils.setUpStatusBarColor(window, ResourcesCompat.getColor(resources, android.R.color.white, null))
    }

    private fun initComponents(){
        setContentView(R.layout.activity_rating)
        activity_rating_raticng_recycler.layoutManager = LinearLayoutManager(this)
        activity_rating_raticng_recycler.adapter = RatingRecyclerAdapter()
    }
}
