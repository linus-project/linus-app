package com.example.linusapp.ui.distro


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.linusapp.ImageAdapterDistribuicao
import com.example.linusapp.databinding.FragmentDistroBinding
import com.example.linusapp.utils.Api
import com.example.linusapp.vo.DistroVO
import com.example.linusapp.vo.UserVO
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class DistroFragment : Fragment() {

    private lateinit var binding: FragmentDistroBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var adapter: ImageAdapterDistribuicao
    private lateinit var userVO: UserVO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistroBinding.inflate(inflater, container, false)
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
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
            }
        })
        return binding.root
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
        viewPager2 = binding.viewPager
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
                            context?.packageName
                        )
                    }
                    adapter = ImageAdapterDistribuicao(distroList, viewPager2)
                    viewPager2.adapter = adapter
                    viewPager2.offscreenPageLimit = 4
                    viewPager2.clipToPadding = false
                    viewPager2.clipChildren = false
                    viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}