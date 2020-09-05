# SmartRecyclerView - a smart, robust and scalable way to handle lists in android.


Following exmaple of code allows to render a text based list


        recyclerView.smartBinder(object : SmartRecyclerView.SmartViewBinder<Option, TextView> {

            override fun itemView(context: Context): TextView {
                return TextView(context).clickEffect(R.color.colorPrimary)
            }

            override fun bind(view: TextView, option: Option) {
                view.sizeLarge().basicPadding()
                view.text = option.name
            }
        }).vertically(options)
        
        
    
    
### The above code example represents this:

![Simple Text Base List](https://github.com/harsewaksingh13/SmartRecyclerView/blob/master/Screenshot_1599265092.png)


### Set a click listener

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

#### How to change horizontally or Grid
        recyclerView.horizontally(options)
        //or grid
        recyclerView.grid(options, 2)
        
#### The above code example represents this:

![Simple Text Base Horizontal List](https://github.com/harsewaksingh13/SmartRecyclerView/blob/master/Screenshot_1599265098.png)

![Simple Text Base Grid](https://github.com/harsewaksingh13/SmartRecyclerView/blob/master/Screenshot_1599265102.png)

       
