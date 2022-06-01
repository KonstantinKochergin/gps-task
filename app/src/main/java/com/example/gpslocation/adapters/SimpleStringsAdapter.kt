package com.example.gpslocation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gpslocation.R

class SimpleStringsAdapter(private val strings: Array<String>) : RecyclerView.Adapter<SimpleStringsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView

        init {
            tv = view.findViewById(R.id.string_item_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.string_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = strings[position]
    }

    override fun getItemCount(): Int {
        return strings.size
    }
}