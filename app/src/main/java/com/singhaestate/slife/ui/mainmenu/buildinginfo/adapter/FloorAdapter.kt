package com.singhaestate.slife.ui.mainmenu.buildinginfo.adapter

import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singhaestate.slife.R
import com.singhaestate.slife.util.Contextor
import kotlinx.android.synthetic.main.text_list_item.view.*
import java.util.ArrayList
import android.widget.LinearLayout


class FloorAdapter(val items: ArrayList<String>, val context: Activity, var floorSelect: String,
                   val boolean: Boolean, val btnlistener: BtnClickListener) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(Contextor.context).inflate(R.layout.text_list_item, parent, false))
    }

    var row_index: Int? = null
    var mClickListener: BtnClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mClickListener = btnlistener
        holder.textFloor?.text = items[position]
        holder.textFloor.setOnClickListener(View.OnClickListener {
            row_index = position
            if (mClickListener != null)
                mClickListener?.onBtnClick(position, items[position])
            notifyDataSetChanged()
        })

        if (row_index == position) {
            if (boolean) {
                holder.layoutItem.layoutParams = setLayout()
                holder.textFloor.setBackgroundResource(R.drawable.floor_selector)
            } else {
                holder.layoutItem.setBackgroundResource(R.color.primary)
            }
            holder.textFloor.setTextColor(Color.parseColor("#ffffff"))
        } else {
            if (items[position].equals(floorSelect)) {
                floorSelect = ""
                if (boolean) {
                    holder.layoutItem.layoutParams = setLayout()
                    holder.textFloor.setBackgroundResource(R.drawable.floor_selector)
                } else {
                    holder.layoutItem.setBackgroundResource(R.color.primary)
                }
                holder.textFloor.setTextColor(Color.parseColor("#ffffff"))
            } else {
                if (boolean) {
                    holder.layoutItem.layoutParams = setLayout()
                    holder.textFloor.setBackgroundResource(R.color.white)
                } else {
                    holder.layoutItem.setBackgroundResource(R.color.white)
                }
                holder.textFloor.setTextColor(Color.parseColor("#cccccc"))
            }
        }
    }


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    private fun setLayout(): ViewGroup.LayoutParams? {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        p.width = (width / 4.5).toInt()
        return p
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val textFloor = view.text1
    val layoutItem = view.layoutItem
}

interface BtnClickListener {
    fun onBtnClick(position: Int, floor: String)
}