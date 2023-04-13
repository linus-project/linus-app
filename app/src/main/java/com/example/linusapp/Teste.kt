package com.example.linusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.viewpager.widget.ViewPager

class Teste : AppCompatActivity() {

    private lateinit var actionBar:ActionBar

    private lateinit var myModelList: ArrayList<MyModel>

    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste)

        actionBar = this.supportActionBar!!

        loadCards()
        findViewById<ViewPager>(R.id.viewPager).addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val title = myModelList[position].title
                actionBar.title = title
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun loadCards() {
        myModelList = ArrayList()


        myModelList.add(MyModel("Distribuição Um",
            "Distribuição UmDistribuição UmDistribuição UmDistribuição Um",
            R.drawable.basico))
        myModelList.add(MyModel("Distribuição Um",
            "Distribuição UmDistribuição UmDistribuição UmDistribuição Um",
            R.drawable.basico))
        myModelList.add(MyModel("Distribuição Um",
            "Distribuição UmDistribuição UmDistribuição UmDistribuição Um",
            R.drawable.basico))
        myModelList.add(MyModel("Distribuição Um",
            "Distribuição UmDistribuição UmDistribuição UmDistribuição Um",
            R.drawable.basico))

        myAdapter = MyAdapter(this,myModelList)

        findViewById<ViewPager>(R.id.viewPager).adapter
        findViewById<ViewPager>(R.id.viewPager).setPadding(100, 0, 100, 0)
    }
}