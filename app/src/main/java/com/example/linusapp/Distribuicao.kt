package com.example.linusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.linusapp.utils.Api
import com.example.linusapp.vo.ContentVO
import com.example.linusapp.vo.DistroVO
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class Distribuicao : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var adapter: ImageAdapterDistribuicao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distribuicao)

        init()
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
            }
        })
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable, 2000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init() {
        viewPager2 = findViewById(R.id.viewPager)
        handler = Handler(Looper.myLooper()!!)
        val service = Api.getDistroApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getDistro()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val distroList: MutableList<DistroVO> = gson.fromJson(prettyJson, Array<DistroVO>::class.java).toMutableList()
                    distroList.forEach {
                        it.image = resources.getIdentifier(
                            it.distroName.lowercase(),
                            "drawable",
                            packageName
                        )
                    }
                    adapter = ImageAdapterDistribuicao(distroList, viewPager2)
                    viewPager2.adapter = adapter
                    viewPager2.offscreenPageLimit = 4
                    viewPager2.clipToPadding = false
                    viewPager2.clipChildren = false
                    viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}