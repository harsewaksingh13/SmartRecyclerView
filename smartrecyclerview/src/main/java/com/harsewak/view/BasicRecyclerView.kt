package com.harsewak.view


import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by harsewaksingh on 02/01/17.
 *
 * @author harsewaksingh
 */

open class BasicRecyclerView<A : BaseRecycledAdapter<T, H>, T, H> : RecyclerView {


    protected var adapter: A? = null

    internal var onItemClickListener: BaseRecycledAdapter.OnItemClickListener<T>? = null

    internal var onItemPositionClickListener: BaseRecycledAdapter.OnItemPositionClickListener<T>? = null
    internal var onItemLongClickListener: BaseRecycledAdapter.OnItemLongClickListener<T>? = null

    var items: MutableList<T>
        get() = adapter?.items ?: ArrayList()
        private set(items) {
            adapter?.items = items
            setAdapter()
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        // setLayoutManager(create(VERTICAL));
        // LayoutInflater.from(context).inflate(R.layout.view_recycler_view, this, true);
        // ButterKnife.bind(this);
    }

    /**
     * @param a - with items
     */
    fun setVerticalAdapter(a: A) {
        setAdapter(a, LinearLayoutManager(context, VERTICAL, false))
    }

    /**
     * @param a     - adapter without items
     * @param items - items to map with adapter
     */
    fun setVerticalAdapter(a: A, items: MutableList<*>) {
        setAdapter(a, items, LinearLayoutManager(context, VERTICAL, false))
    }

    /**
     * @param a - with items
     */
    fun setHorizontalAdapter(a: A) {
        setAdapter(a, create(HORIZONTAL))
    }

    /**
     * @param a     - adapter without items
     * @param items - items to map with adapter
     */
    fun setHorizontalAdapter(a: A, items: MutableList<*>) {
        setAdapter(a, items, create(HORIZONTAL))
    }

    /**
     *
     */
    fun setGridAdapter(a: A, span: Int) {
        setAdapter(a, GridLayoutManager(context, span))
    }


    fun setGridAdapter(a: A, items: MutableList<*>, span: Int) {
        setAdapter(a, items, GridLayoutManager(context, span))
    }

    fun setGridAdapter(a: A, items: MutableList<*>, spanCount: Int, spanSizeLookup: GridLayoutManager.SpanSizeLookup) {
        val gridLayoutManager = GridLayoutManager(context, spanCount, VERTICAL, false)
        gridLayoutManager.spanSizeLookup = spanSizeLookup
        setAdapter(a, items, gridLayoutManager)
    }

    fun setGridAdapter(a: A, items: MutableList<*>, span: Int, orientation: Int) {
        setAdapter(a, items, GridLayoutManager(context, span, orientation, false))
    }

    private fun createReverse(orientation: Int): LayoutManager {
        return create(orientation, true)
    }

    private fun create(orientation: Int, reverse: Boolean = false): LayoutManager {
        return LinearLayoutManager(context, orientation, reverse)
    }

    @Suppress("UNCHECKED_CAST")
    private fun setAdapter(adapter: A, items: MutableList<*>, layoutManager: LayoutManager) {
        initRecyclerView(adapter, layoutManager)
        val castedItems = items as? MutableList<T>
        castedItems?.let {
            this.items = it
        }
    }

    private fun setAdapter(adapter: A, layoutManager: LayoutManager) {
        initRecyclerView(adapter, layoutManager)
        setAdapter()
    }

    private fun initRecyclerView(a: A, layoutManager: LayoutManager) {
        setLayoutManager(layoutManager)
        itemAnimator = DefaultItemAnimator()
        setHasFixedSize(true)
        this.adapter = a
    }

    private fun setAdapter() {
        onItemClickListener?.let {
            adapter?.setOnItemClickListener(it)
        }
        adapter?.setOnItemPositionClickListener(onItemPositionClickListener)
        adapter?.setOnItemLongClickListener(onItemLongClickListener)
        setAdapter(adapter)
    }

    fun getItem(position: Int): T? {
        return adapter?.items?.get(position)
    }

    fun setOnItemClickListener(onItemClickListener: BaseRecycledAdapter.OnItemClickListener<*>) {
        this.onItemClickListener = onItemClickListener as BaseRecycledAdapter.OnItemClickListener<T>
    }

    fun setOnItemPositionClickListener(onItemPositionClickListener: BaseRecycledAdapter.OnItemPositionClickListener<T>) {
        this.onItemPositionClickListener = onItemPositionClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: BaseRecycledAdapter.OnItemLongClickListener<T>) {
        this.onItemLongClickListener = onItemLongClickListener
    }


}
