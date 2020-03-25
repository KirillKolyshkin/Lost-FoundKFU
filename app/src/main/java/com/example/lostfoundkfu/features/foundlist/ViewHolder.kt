package com.example.lostfoundkfu.features.foundlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lostfoundkfu.data.Items.LostItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_lost_found.view.*
import java.text.SimpleDateFormat

class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(item: LostItem){
        with(itemView){
            tv_item_name.text = item.name
            tv_place.text = item.place
            tv_date.text = SimpleDateFormat("dd/M/yyyy").format(item.date)
            Picasso.with(context).load(item.imageUrl).into(iv_item_icon)
        }
    }
}
