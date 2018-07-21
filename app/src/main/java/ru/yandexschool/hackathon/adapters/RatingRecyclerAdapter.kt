package ru.yandexschool.hackathon.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_rating_item.view.*
import ru.yandexschool.hackathon.R
import ru.yandexschool.hackathon.entity.Rating


class RatingRecyclerAdapter() : RecyclerView.Adapter<RatingRecyclerAdapter.ViewHolder>() {

    var mList: ArrayList<Rating> = ArrayList<Rating>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.recycler_rating_item, p0, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPosition.text = mList.get(position).position.toString()
        holder.txtName.text = mList.get(position).name
        holder.txtScore.text = mList.get(position).score.toString()
    }

    override fun getItemCount(): Int = mList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPosition = view.recycler_rating_item_number
        val txtName = view.recycler_rating_item_name
        val txtScore = view.recycler_rating_item_score
    }

    fun addAll(items: ArrayList<Rating>){
        mList.addAll(items)
        notifyDataSetChanged()
    }
}