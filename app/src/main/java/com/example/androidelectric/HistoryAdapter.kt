package com.example.androidelectric

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.androidelectric.data.model.MeterReading
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter(val userList: ArrayList<MeterReading>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.history_element, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindItems(user: MeterReading) {
            val textViewUserId = itemView.findViewById(R.id.textViewUserId) as TextView
            val textViewDate  = itemView.findViewById(R.id.textViewDate) as TextView
            val textViewVoltage = itemView.findViewById(R.id.textViewVoltage) as TextView
            val textViewCurrent  = itemView.findViewById(R.id.textViewCurrent) as TextView
            val textViewFrequency  = itemView.findViewById(R.id.textViewFrequency) as TextView
            val textViewPower  = itemView.findViewById(R.id.textViewPower) as TextView
            val textViewEnergy  = itemView.findViewById(R.id.textViewEnergy) as TextView

            val simpleDateFormat = SimpleDateFormat("dd/MM/yyy h:mm:ss a")
            val date = Date(user.Record.time.toLong())
            val time = simpleDateFormat.format(date)
            textViewUserId.text = user.Record.userId
            textViewDate.text = time
            textViewVoltage.text = user.Record.voltage
            textViewCurrent.text = user.Record.current
            textViewFrequency.text = user.Record.frequency
            textViewPower.text = user.Record.power
            textViewEnergy.text = user.Record.energy
        }
    }
}


