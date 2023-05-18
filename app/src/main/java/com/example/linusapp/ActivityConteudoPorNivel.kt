package com.example.linusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ActivityConteudoPorNivel : AppCompatActivity() {

    private lateinit var viewPagerBasico: ViewPager2
    private lateinit var viewPagerIntermediario: ViewPager2
    private lateinit var viewPagerAvancado: ViewPager2

    private lateinit var handlerBasico: Handler
    private lateinit var handlerIntermediario: Handler
    private lateinit var handlerAvancado: Handler

    private lateinit var imageListBasico: ArrayList<Int>
    private lateinit var imageListIntermediario: ArrayList<Int>
    private lateinit var imageListAvancado: ArrayList<Int>

    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conteudo_por_nivel)

        initBasico()
        initIntermediario()
        initAvancado()
        setUpTransformerBasico()
        setUpTransformerIntermediario()
        setUpTransformerAvancado()

        viewPagerBasico.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handlerBasico.removeCallbacks(runnableBasico)
            }
        })

        viewPagerIntermediario.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handlerIntermediario.removeCallbacks(runnableIntermediario)
            }
        })

        viewPagerAvancado.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handlerAvancado.removeCallbacks(runnableAvancado)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handlerBasico.removeCallbacks(runnableBasico)
    }

    override fun onResume() {
        super.onResume()

        handlerBasico.postDelayed(runnableBasico, 2000)
    }


    private val runnableBasico = Runnable {
        viewPagerBasico.currentItem = viewPagerBasico.currentItem + 1
    }

    private val runnableIntermediario = Runnable {
        viewPagerIntermediario.currentItem = viewPagerIntermediario.currentItem + 1
    }

    private val runnableAvancado = Runnable {
        viewPagerAvancado.currentItem = viewPagerAvancado.currentItem + 1
    }

    private fun setUpTransformerBasico() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPagerBasico.setPageTransformer(transformer)
    }

    private fun setUpTransformerIntermediario() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPagerIntermediario.setPageTransformer(transformer)
    }

    private fun setUpTransformerAvancado() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPagerAvancado.setPageTransformer(transformer)
    }

    private fun initBasico() {
        viewPagerBasico = findViewById(R.id.viewPagerBasico)
        handlerBasico = Handler(Looper.myLooper()!!)
        imageListBasico = ArrayList()

        imageListBasico.add(R.drawable.nivel_basico)
        imageListBasico.add(R.drawable.nivel_basico)
        imageListBasico.add(R.drawable.nivel_basico)

        adapter = ImageAdapter(imageListBasico, viewPagerBasico)

        viewPagerBasico.adapter = adapter
        viewPagerBasico.offscreenPageLimit = 4
        viewPagerBasico.clipToPadding = false
        viewPagerBasico.clipChildren = false
        viewPagerBasico.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

    private fun initIntermediario() {
        viewPagerIntermediario = findViewById(R.id.viewPagerIntermediario)
        handlerIntermediario = Handler(Looper.myLooper()!!)
        imageListIntermediario = ArrayList()

        imageListIntermediario.add(R.drawable.nivel_intermediario)
        imageListIntermediario.add(R.drawable.nivel_intermediario)
        imageListIntermediario.add(R.drawable.nivel_intermediario)

        adapter = ImageAdapter(imageListIntermediario, viewPagerIntermediario)

        viewPagerIntermediario.adapter = adapter
        viewPagerIntermediario.offscreenPageLimit = 4
        viewPagerIntermediario.clipToPadding = false
        viewPagerIntermediario.clipChildren = false
        viewPagerIntermediario.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

    private fun initAvancado() {
        viewPagerAvancado = findViewById(R.id.viewPagerAvancado)
        handlerAvancado = Handler(Looper.myLooper()!!)
        imageListAvancado = ArrayList()

        imageListAvancado.add(R.drawable.nivel_avancado)
        imageListAvancado.add(R.drawable.nivel_avancado)
        imageListAvancado.add(R.drawable.nivel_avancado)

        adapter = ImageAdapter(imageListAvancado, viewPagerAvancado)

        viewPagerAvancado.adapter = adapter
        viewPagerAvancado.offscreenPageLimit = 4
        viewPagerAvancado.clipToPadding = false
        viewPagerAvancado.clipChildren = false
        viewPagerAvancado.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }
}