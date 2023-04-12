package com.example.linusapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter

class MyAdapter(private val context: Context, private val myModelArrayList: ArrayList<MyModel>) : PagerAdapter() {
    override fun getCount(): Int {
        return myModelArrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item, container, false)


        val model = myModelArrayList[position]
        val title = model.title
        val description = model.description
        val image = model.image

        view.findViewById<ImageView>(R.id.bannerIv)
        view.findViewById<TextView>(R.id.titleTv)
        view.findViewById<TextView>(R.id.descriptionTv)

        view.setOnClickListener{
            Toast.makeText(context, "$title \n $description", Toast.LENGTH_SHORT).show()
        }

        container.addView(view, position)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}