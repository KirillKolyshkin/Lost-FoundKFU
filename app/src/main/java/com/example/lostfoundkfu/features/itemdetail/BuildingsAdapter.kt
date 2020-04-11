package com.example.lostfoundkfu.features.itemdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.BuildingWithoutFlag
import kotlinx.android.synthetic.main.place_item.view.*

class BuildingsAdapter: RecyclerView.Adapter<BuildingsAdapter.BuildingViewHolder>() {

    var list: ArrayList<BuildingWithoutFlag> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuildingsAdapter.BuildingViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_building, parent, false)
        return BuildingViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BuildingsAdapter.BuildingViewHolder, position: Int) {
        val currentUser = list[position]
        holder.bindViews(currentUser)
    }

    inner class BuildingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(building: BuildingWithoutFlag) {
            with(itemView) {
                tv_name.text = building.name
                Glide.with(context).load(building.image).into(iv_user_icon)
            }
        }

    }
}
