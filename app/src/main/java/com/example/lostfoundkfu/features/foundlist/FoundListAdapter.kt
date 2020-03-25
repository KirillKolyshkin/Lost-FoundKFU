package com.example.lostfoundkfu.features.foundlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.LostItem

class FoundListAdapter(private var onItemClick: (LostItem) -> Unit) : RecyclerView.Adapter<ViewHolder>(){

    var list: ArrayList<LostItem> = arrayListOf()
    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lost_found, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
        holder.itemView.setOnClickListener { onItemClick(list[position]) }
    }
}
