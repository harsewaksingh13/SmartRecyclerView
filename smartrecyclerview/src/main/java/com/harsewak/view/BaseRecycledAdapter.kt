package com.harsewak.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Created by harsewaksingh on 06/06/16.
 *
 * @author harsewaksingh
 */
abstract class BaseRecycledAdapter<T, H> : RecyclerView.Adapter<BaseRecycledHolder<T>> {

    var items: MutableList<T> = ArrayList()

    internal var onItemPositionClickListener: OnItemPositionClickListener<T>? = null
    internal var onItemLongClickListener: OnItemLongClickListener<T>? = null

    private val onItemClickListeners = ArrayList<OnItemClickListener<T>>()


    constructor() {
        items = ArrayList()
    }

    constructor(items: MutableList<T>) {
        this.items = items
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        addOnItemClickListener(onItemClickListener)
    }

    fun setOnItemPositionClickListener(onItemPositionClickListener: OnItemPositionClickListener<T>?) {
        this.onItemPositionClickListener = onItemPositionClickListener
    }

    fun addOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        onItemClickListeners.add(onItemClickListener)
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>?) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    protected fun inflate(@LayoutRes id: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(id, parent, false)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun onBindHolder(h: H, t: T, position: Int) {
        (h as BaseRecycledHolder<T>).bind(t)
    }

    override fun getItemCount(): Int {
        try {
            return items.size
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return 0
    }

    override fun onBindViewHolder(holder: BaseRecycledHolder<T>, position: Int) {
        val t = items[position]
        onBindHolder(holder as H, t, position)
        if (onItemClickListeners.size > 0 || onItemPositionClickListener != null) {
            if (onItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener { v ->
                    onItemLongClickListener!!.onItemLongClick(t, position)
                    true
                }
            }
            holder.itemView.setOnClickListener {
                onItemPositionClickListener?.onItemClick(t, position)
                for (onItemClickListener in onItemClickListeners)
                    onItemClickListener.onItemClick(t)
            }
        }
    }

    fun addAll(moreItems: List<T>) {
        items.addAll(moreItems)
    }

    /**
     * make sure you have override equal method of your object
     *
     * @param t - object to update
     */
    fun updateAndNotify(t: T): Boolean {
        val index = items.indexOf(t)
        if (index >= 0) {
            items[index] = t
            notifyItemChanged(index)
            return true
        }
        return false
    }

    fun add(t: T): Int {
        items.add(t)
        return itemCount - 1
    }

    fun addAndNotify(t: T) {
        val index = add(t)
        notifyItemInserted(index)
    }

    fun addOrUpdateAndNotify(t: T): Int {
        var index = items.indexOf(t)
        if (index >= 0) {
            items[index] = t
            notifyItemChanged(index)
        } else {
            index = add(t)
            notifyItemInserted(index)
        }
        return index
    }

    fun deleteAndNotify(position: Int): Boolean {
        if (position >= 0 && position < items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
            return true
        }
        return false
    }

    fun deleteAndNotify(t: T): Boolean {
        val index = items.indexOf(t)
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
            return true
        }
        return false
    }

    interface OnItemClickListener<T> {
        fun onItemClick(t: T)
    }

    interface OnItemPositionClickListener<T> {
        fun onItemClick(t: T, index: Int)
    }

    interface OnItemLongClickListener<T> {
        fun onItemLongClick(t: T, index: Int)
    }

}

