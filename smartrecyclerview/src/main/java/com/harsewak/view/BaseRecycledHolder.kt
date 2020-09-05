package com.harsewak.view

import androidx.recyclerview.widget.RecyclerView
import android.view.View



/**
 * Created by harsewaksingh on 07/06/16.
 *
 * @author harsewaksingh
 */
class BaseRecycledHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(t: T) {}
}