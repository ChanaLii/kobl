package com.kobl.atollspeed.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.widget.Toast
import cn.hutool.http.HttpUtil
import com.kobl.atollspeed.BuildConfig.DEBUG
import com.kobl.atollspeed.R
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val fruitList = ArrayList<Fruit>()

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFruits() // 初始化水果数据
        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)

        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->

        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val fruit = fruitList[position]
            //"https://api.triascloud.com/monitor/count"
            val url = "http://10.168.1.238?filename=" + fruit.name;
            run(url)

            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
        }
        Log.d("MainActivity", "onCreate execute")
    }


    private fun initFruits() {
        repeat(1) {
            fruitList.add(Fruit("menue.txt", R.drawable.apple_pic))
            fruitList.add(Fruit("settings.txt", R.drawable.banana_pic))
            fruitList.add(Fruit("favorites.txt", R.drawable.orange_pic))
            fruitList.add(Fruit("develop.txt", R.drawable.watermelon_pic))
            fruitList.add(Fruit("parameter.txt", R.drawable.pear_pic))
            fruitList.add(Fruit("service.txt", R.drawable.grape_pic))
            fruitList.add(Fruit("warning.txt", R.drawable.pineapple_pic))
//            fruitList.add(Fruit("Strawberry", R.drawable.strawberry_pic))
//            fruitList.add(Fruit("Cherry", R.drawable.cherry_pic))
//            fruitList.add(Fruit("Mango", R.drawable.mango_pic))
        }
    }

    private fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$name: $value")
            }

            println(response.body!!.string())
        }
    }

}