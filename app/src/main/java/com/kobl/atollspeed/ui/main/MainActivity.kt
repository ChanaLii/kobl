package com.kobl.atollspeed.ui.main

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kobl.atollspeed.R
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy


class MainActivity : AppCompatActivity() {

    private val fruitList = ArrayList<Fruit>()

    private var client: OkHttpClient = OkHttpClient()

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        } else {
            System.out.println(Build.VERSION.SDK_INT)
        }

    }

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
            val url = "http://10.168.1.50?filename=" + fruit.name
            val content = sendHTTPRequest(url)
            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, content, Toast.LENGTH_LONG).show()
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

    private fun sendHTTPRequest(url: String): String {
        var content = ""
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        response.use { r ->
            if (!r.isSuccessful) content = ("Unexpected code $r")

            for ((name, value) in r.headers) {
                println("$name: $value")
            }
            content = r.body!!.string()
            println(content)
            return content
        }
    }

}