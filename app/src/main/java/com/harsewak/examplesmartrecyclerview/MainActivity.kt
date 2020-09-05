package com.harsewak.examplesmartrecyclerview

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harsewak.examplesmartrecyclerview.view.basicPadding
import com.harsewak.examplesmartrecyclerview.view.clickEffect
import com.harsewak.examplesmartrecyclerview.view.sizeLarge
import com.harsewak.view.BaseRecycledAdapter
import com.harsewak.view.SmartRecyclerView
import kotlinx.android.synthetic.main.activity_layout_recycler_view.*

data class Option(val name: String)


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_recycler_view)
    }

    override fun onStart() {
        super.onStart()
        val options = ArrayList<Option>()
        options.add(Option("Listing Vertically"))
        options.add(Option("Listing Horizontally"))
        options.add(Option("Grid Items"))

        recyclerView.showLines()
        recyclerView.setOnItemClickListener(object : BaseRecycledAdapter.OnItemClickListener<Option> {
            override fun onItemClick(t: Option) {
                Toast.makeText(this@MainActivity, t.name, Toast.LENGTH_SHORT).show()
                when {
                    t.name.contains("Horizontally") -> recyclerView.horizontally(options)
                    t.name.contains("Grid") -> recyclerView.grid(options, 2)
                    else -> recyclerView.vertically(options)
                }

            }
        })
        recyclerView.smartBinder(object : SmartRecyclerView.SmartViewBinder<Option, TextView> {

            override fun itemView(context: Context): TextView {
                return TextView(context).clickEffect(R.color.colorPrimary)
            }

            override fun bind(view: TextView, option: Option) {
                view.sizeLarge().basicPadding()
                view.text = option.name
            }
        }).vertically(options)


    }


}
