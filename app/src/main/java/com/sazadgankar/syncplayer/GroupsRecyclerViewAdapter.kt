package com.sazadgankar.syncplayer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.active_groups_row.view.*

class GroupsRecyclerViewAdapter(private val dataset: ArrayList<String>) :
        RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val frameLayout: FrameLayout) : RecyclerView.ViewHolder(frameLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GroupsRecyclerViewAdapter.ViewHolder {

        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.active_groups_row, parent, false) as FrameLayout
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.frameLayout.active_groups_row_text.text = dataset[position]
    }

    override fun getItemCount() = dataset.size

}