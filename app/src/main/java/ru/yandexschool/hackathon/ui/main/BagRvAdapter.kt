package ru.yandexschool.hackathon.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.yandexschool.hackathon.R
import java.util.*


interface BagRvListener {
    fun finishGame()
}

class BagRvAdapter(val listener: BagRvListener) : RecyclerView.Adapter<BagRvAdapter.ViewHolder>() {

    private val data = ArrayList<ItemField>()
    private val random = Random(System.currentTimeMillis())

    private var isGameFinished = false

    fun addItemToField(item: ItemField) {

        val empty = data.count {
            it is EmptyField
        }

        if (empty == 0) {
            isGameFinished = true
            listener.finishGame()
            return
        }

        var pos = Math.abs(random.nextInt() % empty)

        for ((i, x) in data.withIndex()) {
            if (x is EmptyField) {
                pos--
            }
            if (pos < 0) {
                data[i] = item
                notifyItemChanged(i)
                break
            }
        }
    }

    init {
        for (i in 0 until 16) {
            data.add(EmptyField())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_field, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fill(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val img: ImageView = view.findViewById(R.id.img)

        fun fill(item: ItemField) {
            img.setImageResource(item.img)

            view.setOnClickListener {
                if (!isGameFinished) {
                    when (data[adapterPosition]) {
                        is SmallBag -> {
                            data[adapterPosition] = EmptyField()
                            notifyItemChanged(adapterPosition)
                        }
                        is EvilBag -> {
                            data[adapterPosition] = EmptyField()
                            notifyItemChanged(adapterPosition)
                            addItemToField(SmallBag())
                            addItemToField(SmallBag())
                        }
//                    is EmptyField -> data[adapterPosition] = SmallBag()
                    }
                }
            }
        }
    }


}