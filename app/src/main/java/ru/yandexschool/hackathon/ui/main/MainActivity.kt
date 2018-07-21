package ru.yandexschool.hackathon.ui.main

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.yandexschool.hackathon.R
import ru.yandexschool.hackathon.utils.Item
import ru.yandexschool.hackathon.utils.Items
import ru.yandexschool.hackathon.utils.Proger
import ru.yandexschool.hackathon.utils.Progers
import ru.yandexschool.hackathon.entity.Rating
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var gameIsFinished = false

    val random = Random(System.currentTimeMillis())
    private var speed = 2000L
    private var score = 0
    private var progersSpeed = 3000L


    private val ITEMS_COUNT = 4

    var progers = Array<Proger?>(4, { null })
    var items = Array<Item?>(4, { null })

    private lateinit var musBackground: MediaPlayer
    private lateinit var lampFail: MediaPlayer
    private lateinit var lose: MediaPlayer
    private lateinit var progersUse: MediaPlayer

    //state
    var itemSelected: Items = Items.COFFEE
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        musBackground = MediaPlayer.create(this, R.raw.background)
        musBackground.isLooping = true
        musBackground.start()

        lampFail = MediaPlayer.create(this, R.raw.lamp_fail)
        lose = MediaPlayer.create(this, R.raw.lose)
        progersUse = MediaPlayer.create(this, R.raw.progers_use)

        //progers

        val progerLayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        progerLayoutParams.weight = 1.0f
        progerLayoutParams.bottomMargin = 10
        progerLayoutParams.bottomMargin = 10
        progerLayoutParams.topMargin = 10
        progerLayoutParams.marginStart = 10
        progerLayoutParams.marginEnd = 10

        for (i in 0 until ITEMS_COUNT) {
            val image = ImageView(this)
            image.layoutParams = progerLayoutParams
            image.setImageResource(R.drawable.bug_hard)

            progers[i] = Proger(image, Progers.WORKING)

            progers[i]?.imageView?.setOnClickListener({
                onProgerClicked(progers[i]?.type)
            })
        }

        for (i in 0 until ITEMS_COUNT) {
            ll_progers.addView(progers[i]?.imageView)
        }


        //items

        val itemsLayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        itemsLayoutParams.weight = 1.0f
        itemsLayoutParams.bottomMargin = 10
        itemsLayoutParams.bottomMargin = 10
        itemsLayoutParams.topMargin = 10
        itemsLayoutParams.marginStart = 10
        itemsLayoutParams.marginEnd = 10

        for (i in 0 until ITEMS_COUNT) {
            val image = ImageView(this)
            image.id = i
            image.layoutParams = itemsLayoutParams
            image.setImageResource(R.drawable.bug_lite)

            items[i] = Item(image, Items.COFFEE)

            items[i]?.imageView?.setOnClickListener({

                onItemClicked(items[i]?.type)
            })
        }

        //item types
        items[0]?.type = Items.COFFEE
        items[0]?.imageView?.setImageResource(R.drawable.coffee)
        items[1]?.type = Items.CAT
        items[1]?.imageView?.setImageResource(R.drawable.cat)
        items[2]?.type = Items.PUNCH
        items[2]?.imageView?.setImageResource(R.drawable.kick)
        items[3]?.type = Items.PROGER

        for (i in 0 until ITEMS_COUNT) {
            ll_items.addView(items[i]?.imageView)
        }

        //bugs

        bagFieldRv.layoutManager = GridLayoutManager(this, 4)
        bagFieldRv.adapter = BugRvAdapter(object : BugRvListener {
            override fun finishGame() {
                this@MainActivity.finishGame()
            }

            override fun addRating(rating: Int) {
                score += rating

                when {
                    rating > 0 -> progersUse.start()
                    rating < 0 -> lampFail.start()
                }

                score = Math.max(0, score)
                updateScore()
            }
        })

        updateScore()
        startBugTimer()
        startProgersTimer()
    }

    private fun updateScore() {
        val s = "Score: $score ฿"
        tvScore.text = s
    }

    private fun finishGame() {
        lose.start()
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

    override fun onStop() {
        super.onStop()

        try {
            musBackground.stop()
        } catch (ex: Exception) {

        }

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

    private fun startProgersTimer() {

        Observable
                .just(1)
                .delay(progersSpeed, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.wtf("bugbug", "new")
                    //speed -= 300
//                    speed = Math.max(speed, 300L)

                    val randomProgerIndex = Math.abs(random.nextInt() % 4)
                    val randomTypeIndex = Math.abs(random.nextInt() % 4)

                    val newProgerType = progerTypeByIndex(randomTypeIndex)

                    if (progers[randomProgerIndex]?.type == Progers.WORKING) {
                        progers[randomProgerIndex]?.type = newProgerType

                        //Toast.makeText(this, "change proger $randomProgerIndex to state $newProgerType", Toast.LENGTH_SHORT).show()

                        //TODO: update proger pic
                        updateProgerPic(progers[randomProgerIndex]?.imageView, newProgerType)

                        //todo: update active progers count
                        updateActiveProgersCount(progers)
                    }

                    startProgersTimer()
                }

    }

    //DIMA FUNC
    private fun updateActiveProgers(count: Int) {

    }

    //on click listeners

    private fun onItemClicked(itemType: Items?) {
        if (itemType == null) {
            return
        }

        //debug
        Toast.makeText(this, itemType.toString(), Toast.LENGTH_SHORT).show()

        itemSelected = itemType
    }

    private fun onProgerClicked(progerType: Progers?) {
        if (progerType == null) {
            return
        }

        //debug
        //Toast.makeText(this, "Proger:" + progerType.toString(), Toast.LENGTH_SHORT).show()


        //logic

        //TODO: if right
        //updateActiveProgers()

    }


    private fun progerTypeByIndex(index: Int): Progers {
        for (type in Progers.values()) {
            if (type.ordinal == index) {
                return type
            }
        }

        return Progers.WORKING
    }

    private fun updateProgerPic(imageView: ImageView?, progerType: Progers) {
        if (imageView == null) return

        Log.d("tag-----------------", "progertype changed to $progerType")
    }

    private fun updateActiveProgersCount(progers: Array<Proger?>) {
        var count = 0

        for (proger in progers) {
            if (proger?.type == Progers.WORKING) {
                count++
            }
        }

        updateActiveProgers(count)

    }

}
