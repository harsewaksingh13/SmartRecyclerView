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
        
        
    
