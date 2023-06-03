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
import com.example.linusapp.databinding.FragmentHistoryContentBinding
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

class HistoryContentFragment : Fragment() {

    private var _binding: FragmentHistoryContentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var userVO: UserVO
    private lateinit var historyContentViewPager: ViewPager2
    private lateinit var handlerBasico: Handler
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryContentBinding.inflate(inflater, container, false)
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

        init()
        setUpTransformer()

        historyContentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handlerBasico.removeCallbacks(runnable)
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val runnable = Runnable {
        historyContentViewPager.currentItem = historyContentViewPager.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        historyContentViewPager.setPageTransformer(transformer)
    }

    private fun init() {
        historyContentViewPager = binding.historyContentViewpager
        handlerBasico = Handler(Looper.myLooper()!!)
        val service = Api.getContentApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getHistoryContent(userVO.idUser)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val contentList: MutableList<ContentVO> = gson.fromJson(prettyJson, Array<ContentVO>::class.java).toMutableList()
                    contentList.forEach {
                        when (it.fkLevel) {
                            1 -> {
                                it.image = R.drawable.nivel_basico
                            }
                            2 -> {
                                it.image = R.drawable.nivel_intermediario
                            }
                            else -> {
                                it.image = R.drawable.nivel_avancado
                            }
                        }
                    }
                    adapter = ImageAdapter(userVO, contentList, historyContentViewPager)
                    historyContentViewPager.adapter = adapter
                    historyContentViewPager.offscreenPageLimit = 4
                    historyContentViewPager.clipToPadding = false
                    historyContentViewPager.clipChildren = false
                    historyContentViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}