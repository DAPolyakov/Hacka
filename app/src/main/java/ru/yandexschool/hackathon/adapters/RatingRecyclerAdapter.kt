package ru.yandexschool.hackathon.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.yandexschool.hackathon.R
import ru.yandexschool.hackathon.entity.Rating


class RatingRecyclerAdapter() : RecyclerView.Adapter<RatingRecyclerAdapter.ViewHolder>() {

    init{
        //TODO get items from firebase asynchornoulsy
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.recycler_rating_item, p0, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 10

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }
}