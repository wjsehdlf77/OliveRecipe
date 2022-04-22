package com.example.oliverecipe.navigation.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oliverecipe.navigation.API.Row
import com.example.oliverecipe.R


class oliveListAdapter (val rows: List<Row>)
    : RecyclerView.Adapter<oliveListAdapter.ViewHolder>() {


    inner class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(row: Row) {
            val rName: TextView = itemView.findViewById(R.id.rName)
            rName.text = row.rCPNM }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alist_item, parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = rows[position]
        holder.bind(data)
    }
    override fun getItemCount(): Int = rows.size }

