package com.example.colortiles

import android.R.attr.x
import android.R.attr.y
import kotlin.random.Random
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

// тип для координат
data class Coord(val x: Int, val y: Int)
class MainActivity : AppCompatActivity() {

    lateinit var tiles: Array<Array<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //tiles = Array(4) { Array(4) { View(this) } }
        tiles = Array(4) { Array(4) {
            findViewById<View>(resources.getIdentifier("t${it}${0}",
                "id", packageName)) } }
        // TODO: заполнить поле случайными цветами
        initField()
    }

    fun getCoordFromString(s: String): Coord {
        val x = s[0].toString().toInt()
        val y = s[1].toString().toInt()
        Log.d("Coordinates", "X: $x, Y: $y")
        return Coord(x, y)
    }

    fun changeColor(view: View) {
        val brightColor = resources.getColor(R.color.bright)
        val darkColor = resources.getColor(R.color.dark)
        val drawable = view.background as ColorDrawable
        if (drawable.color == brightColor ) {
            view.setBackgroundColor(darkColor)
        } else {
            view.setBackgroundColor(brightColor)
        }
    }

    fun onClick(v: View) {
        val coord = getCoordFromString(v.getTag().toString())
        changeColor(v)

        for (i in 0..3) {
            changeColor(tiles[coord.x][i])
            changeColor(tiles[i][coord.y])
        }

        if (checkVictory()) {
            Log.d("Victory", "Игрок победил!")
        }
    }

    fun checkVictory(): Boolean {
        val firstColor = (tiles[0][0].background as ColorDrawable).color
        for (row in tiles) {
            for (tile in row) {
                if ((tile.background as ColorDrawable).color != firstColor) {
                    return false
                }
            }
        }
        return true
    }

    fun initField() {
        for (row in tiles) {
            for (tile in row) {
                val randomColor = if (Random.nextBoolean()) {
                    resources.getColor(R.color.bright)
                } else {
                    resources.getColor(R.color.dark)
                }
                tile.setBackgroundColor(randomColor)
            }
        }
    }
}