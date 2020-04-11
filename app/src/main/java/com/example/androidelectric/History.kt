package com.example.androidelectric

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidelectric.data.model.MeterReading
import com.example.androidelectric.data.model.ServerAddress
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class History : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.recycleView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val users = ArrayList<MeterReading>()
        val adapter = HistoryAdapter(users)
        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
        fetchUsageHistory(recyclerView)
    }

    fun fetchUsageHistory (recyclerView: RecyclerView) {
        println("Fetching usage history")

        val intent = intent
        val username = intent.getStringExtra("username")
        var url = "https://heroku-electric.herokuapp.com/api/serverDetails"
        var request = Request.Builder().url(url).build()
        var client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                val gson = GsonBuilder().create()
                val ServerData: ServerAddress = gson.fromJson(body, ServerAddress::class.java)
                url = "http://" + ServerData.ip + "/api/usage/" + username
                newApiCall(url, recyclerView)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to get the server data")
            }
        })

    }

    fun newApiCall(urlPath : String, recyclerView: RecyclerView){
        val newRequest = Request.Builder().url(urlPath).build()
        val newClient = OkHttpClient()
        newClient.newCall(newRequest).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()

                val gson = GsonBuilder().create()
                val UsageHistory: List<MeterReading> = gson.fromJson(body, Array<MeterReading>::class.java).toList()

                if(UsageHistory.isEmpty()) {
                    return
                }

                val adapter = HistoryAdapter(UsageHistory as ArrayList<MeterReading>)
                //now adding the adapter to recyclerview
                runOnUiThread {
                    //getting recyclerview from xml
                    val recyclerView = findViewById(R.id.recycleView) as RecyclerView
                    val emptyView = findViewById(R.id.empty_view) as TextView
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to get the usage data")
                println(e)
            }
        })
    }
}