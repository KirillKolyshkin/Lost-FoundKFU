package com.example.lostfoundkfu.features.createlostobject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.Building
import kotlinx.android.synthetic.main.place_item.view.*

class BuildingsAdapter: RecyclerView.Adapter<BuildingsAdapter.BuildingViewHolder>() {

    var list: ArrayList<Building> = arrayListOf()
    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return BuildingViewHolder(v)
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        val currentUser = list[position]
        holder.bindViews(currentUser)
        holder.itemView.checkbox.setOnCheckedChangeListener(null)
        holder.itemView.checkbox.isChecked = currentUser.flag
        holder.itemView
            .checkbox
            .setOnCheckedChangeListener(
                CompoundButton
                .OnCheckedChangeListener(fun(_: CompoundButton, isChecked: Boolean) {
                    list[position].flag = isChecked
                }))
    }

    inner class BuildingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(user: Building) {
            with(itemView) {
                tv_name.text = user.name
                Glide.with(context).load(user.imageUrl).into(iv_user_icon)
            }
        }

    }
}
