package com.example.lostfoundkfu.features.lostlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lostfoundkfu.data.Items.LostItem
import kotlinx.android.synthetic.main.item_lost_found.view.*
import java.text.SimpleDateFormat

class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(item: LostItem){
        var place = ""
        for (_place in item.place){
            place += " $_place"
        }
        with(itemView){
            tv_item_name.text = item.name
            tv_place.text = place
            tv_date.text = SimpleDateFormat("dd/M/yyyy").format(item.date)
            Glide.with(context).load(item.imageUrl).into(iv_item_icon)
        }
    }
}
