package com.harsewak.view


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager

/**
 * A recycler view which is very quick and easy to implement and you can do all things at one place, like adapter and view holder
 */
class SmartRecyclerView<T, V : View> :
    BasicRecyclerView<BaseRecycledAdapter<T, BaseRecycledHolder<T>>, T, BaseRecycledHolder<T>> {
    private var smartBinder: SmartBinder<T, V>? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun showLines() {
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    fun showGridLines() {
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
    }

    @Suppress("UNCHECKED_CAST")
    fun smartBinder(smartBinder: SmartBinder<*, *>): SmartRecyclerView<*, *> {
        this.smartBinder = smartBinder as? SmartBinder<T, V>
        return this
    }

    private fun adapter(): BaseRecycledAdapter<T, BaseRecycledHolder<T>> {
        return DefaultAdapter(this)
    }

    private fun validate() {
        if (this.smartBinder == null) {
            throw NullPointerException("You must implement one of these SmartLayoutBinder - SmartViewBinder to populate your view data")
        }
    }

    fun vertically(items: MutableList<*>) {
        validate()
        setVerticalAdapter(adapter(), items)
    }

    fun horizontally(items: MutableList<*>) {
        validate()
        setHorizontalAdapter(adapter(), items)
    }

    fun grid(items: MutableList<*>, span: Int) {
        validate()
        setGridAdapter(adapter(), items, span)
    }

    fun grid(items: MutableList<*>, span: Int, orientation: Int) {
        validate()
        setGridAdapter(adapter(), items, span, orientation)
    }

    fun grid(items: MutableList<*>, span: Int, spanSizeLookup: GridLayoutManager.SpanSizeLookup) {
        validate()
        setGridAdapter(adapter(), items, span, spanSizeLookup)
    }

    interface SmartBinder<T, V : View> {
        fun bind(view: V, t: T)
    }

    interface SmartViewBinder<T, V : View> : SmartBinder<T, V> {
        fun itemView(context: Context): V
    }

    interface SmartLayoutBinder<T, V : View> : SmartBinder<T, V> {
        @LayoutRes
        fun layout(): Int
    }

    interface SmartMultipleLayoutBinder<T, V : View> : SmartBinder<T, V> {
        fun getItemViewType(i: Int): Int

        @LayoutRes
        fun layout(i: Int): Int
    }

    interface SmartMultipleViewBinder<T, V : View> : SmartBinder<T, V> {
        fun getItemViewType(index: Int): Int

        fun itemView(context: Context, type: Int): V
    }

    internal class DefaultAdapter<T, V : View>(private var smartRecyclerView: SmartRecyclerView<T, V>) :
        BaseRecycledAdapter<T, BaseRecycledHolder<T>>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecycledHolder<T> {

            return when {
                smartRecyclerView.smartBinder is SmartMultipleLayoutBinder<T, V> ->
                    BaseRecycledHolder(
                        inflate(
                            (smartRecyclerView.smartBinder as SmartMultipleLayoutBinder<T, V>).layout(
                                viewType
                            ), parent
                        )
                    )
                smartRecyclerView.smartBinder is SmartMultipleViewBinder<T, V> ->
                    BaseRecycledHolder(
                        (smartRecyclerView.smartBinder as SmartMultipleViewBinder<T, V>).itemView(
                            parent.context,
                            viewType
                        )
                    )
                else -> if (smartRecyclerView.smartBinder is SmartViewBinder<T, V>)
                    BaseRecycledHolder((smartRecyclerView.smartBinder as SmartViewBinder<T, V>).itemView(parent.context))
                else BaseRecycledHolder(
                    inflate(
                        (smartRecyclerView.smartBinder as SmartLayoutBinder<T, V>).layout(),
                        parent
                    )
                )
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when {
                smartRecyclerView.smartBinder is SmartMultipleViewBinder<T, V> -> (smartRecyclerView.smartBinder as SmartMultipleViewBinder<T, V>).getItemViewType(
                    position
                )
                else -> if (smartRecyclerView.smartBinder is SmartMultipleLayoutBinder<T, V>) {
                    (smartRecyclerView.smartBinder as SmartMultipleLayoutBinder<T, V>).getItemViewType(position)
                } else super.getItemViewType(position)
            }
        }


        @Suppress("UNCHECKED_CAST")
        override fun onBindViewHolder(holder: BaseRecycledHolder<T>, position: Int) {
            super.onBindViewHolder(holder, position)
            smartRecyclerView.getItem(position)?.let {
                smartRecyclerView.smartBinder?.bind(holder.itemView as V, it)
            }
        }
    }
}