package com.sazadgankar.syncplayer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.joined_devices_row.view.*


class DevicesRecyclerViewAdapter(private val dataset: ArrayList<String>) :
        RecyclerView.Adapter<DevicesRecyclerViewAdapter.ViewHolder>() {
    var numberOfMembers = 1

    class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.joined_devices_row, parent, false) as LinearLayout
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.linearLayout.joined_devices_row_text.text = dataset[position]
        membersNumber(holder)
    }
    fun membersNumber(holder: ViewHolder) {
        holder.linearLayout.joined_devices_check_box.setOnClickListener {
            if( holder.linearLayout.joined_devices_check_box.isChecked){
                numberOfMembers++
            }
        }
    }
    override fun getItemCount() = dataset.size

}