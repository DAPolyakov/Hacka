package ru.yandexschool.hackathon.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_rating.*
import ru.yandexschool.hackathon.R
import ru.yandexschool.hackathon.Utils
import ru.yandexschool.hackathon.adapters.RatingRecyclerAdapter
import ru.yandexschool.hackathon.entity.Rating

class RatingActivity : AppCompatActivity() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private lateinit var mAdapter: RatingRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        Utils.setUpStatusBarColor(window, ResourcesCompat.getColor(resources, android.R.color.white, null))
    }

    override fun onResume() {
        super.onResume()
        initComponentsListener()
    }

    private fun initComponents(){
        setContentView(R.layout.activity_rating)
        activity_rating_raticng_recycler.layoutManager = LinearLayoutManager(this)
        mAdapter = RatingRecyclerAdapter()
        activity_rating_raticng_recycler.adapter = mAdapter
    }

    private fun initComponentsListener(){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var records = ArrayList<Rating>()
                for(snapShot in p0.children){
                    records.add(snapShot.getValue(Rating::class.java)!!)
                }
                 records.sortBy { it.score }
                mAdapter.addAll(records)
            }
        })
    }
}
