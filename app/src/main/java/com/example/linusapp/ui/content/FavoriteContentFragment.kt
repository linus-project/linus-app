package com.example.linusapp.ui.content

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.linusapp.ImageAdapter
import com.example.linusapp.R
import com.example.linusapp.databinding.FragmentContentByLevelBinding
import com.example.linusapp.databinding.FragmentFavoriteContentBinding
import com.example.linusapp.utils.Api
import com.example.linusapp.vo.ContentVO
import com.example.linusapp.vo.UserVO
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class FavoriteContentFragment : Fragment() {

    private var _binding: FragmentFavoriteContentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var userVO: UserVO

    private lateinit var viewPagerBasico: ViewPager2
    private lateinit var viewPagerIntermediario: ViewPager2
    private lateinit var viewPagerAvancado: ViewPager2

    private lateinit var handlerBasico: Handler
    private lateinit var handlerIntermediario: Handler
    private lateinit var handlerAvancado: Handler

    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteContentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        userVO = UserVO(
            parentFragment?.activity?.intent!!.getLongExtra("idUser", 0),
            parentFragment?.activity?.intent!!.getStringExtra("name").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("username").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("email").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("password").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("genre").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("bornDate").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("phoneNumber").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("adminKey").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("imageCode").toString(),
            parentFragment?.activity?.intent!!.getLongExtra("fkLevel", 0),
            parentFragment?.activity?.intent!!.getIntExtra("isBlocked", 0)
        )

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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        viewPagerBasico = binding.vpFavoritadoBasico
        handlerBasico = Handler(Looper.myLooper()!!)
        val service = Api.getContentApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getFavoriteContentByLevel(userVO.idUser,1)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val contentList: MutableList<ContentVO> = gson.fromJson(prettyJson, Array<ContentVO>::class.java).toMutableList()
                    contentList.forEach {
                        it.image = R.drawable.nivel_basico
                        if (it.content.length > 100) {
                            it.content = it.content.substring(0,100)
                        }
                        it.content += "..."
                    }
                    adapter = ImageAdapter(userVO, contentList, viewPagerBasico)
                    viewPagerBasico.adapter = adapter
                    viewPagerBasico.offscreenPageLimit = 4
                    viewPagerBasico.clipToPadding = false
                    viewPagerBasico.clipChildren = false
                    viewPagerBasico.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initIntermediario() {
        viewPagerIntermediario = binding.vpFavoritadoIntermediario
        handlerIntermediario = Handler(Looper.myLooper()!!)
        val service = Api.getContentApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getFavoriteContentByLevel(userVO.idUser,2)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val contentList: MutableList<ContentVO> = gson.fromJson(prettyJson, Array<ContentVO>::class.java).toMutableList()
                    contentList.forEach {
                        it.image = R.drawable.nivel_intermediario
                        if (it.content.length > 100) {
                            it.content = it.content.substring(0,100)
                        }
                        it.content += "..."
                    }
                    adapter = ImageAdapter(userVO, contentList, viewPagerIntermediario)
                    viewPagerIntermediario.adapter = adapter
                    viewPagerIntermediario.offscreenPageLimit = 4
                    viewPagerIntermediario.clipToPadding = false
                    viewPagerIntermediario.clipChildren = false
                    viewPagerIntermediario.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initAvancado() {
        viewPagerAvancado = binding.vpFavoritadoAvancado
        handlerAvancado = Handler(Looper.myLooper()!!)
        val service = Api.getContentApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getFavoriteContentByLevel(userVO.idUser,3)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val contentList: MutableList<ContentVO> = gson.fromJson(prettyJson, Array<ContentVO>::class.java).toMutableList()
                    contentList.forEach {
                        it.image = R.drawable.nivel_avancado
                        if (it.content.length > 100) {
                            it.content = it.content.substring(0,100)
                        }
                        it.content += "..."
                    }
                    adapter = ImageAdapter(userVO, contentList, viewPagerAvancado)
                    viewPagerAvancado.adapter = adapter
                    viewPagerAvancado.offscreenPageLimit = 4
                    viewPagerAvancado.clipToPadding = false
                    viewPagerAvancado.clipChildren = false
                    viewPagerAvancado.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}