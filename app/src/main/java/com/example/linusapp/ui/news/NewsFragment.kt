package com.example.linusapp.ui.news

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.linusapp.R
import com.example.linusapp.databinding.FragmentNewsBinding
import com.example.linusapp.utils.Api
import com.example.linusapp.vo.NewsVO
import com.example.linusapp.vo.UserVO
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var userVO: UserVO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
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
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val service = Api.getNewsApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getNews()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val newsList: List<NewsVO> = gson.fromJson(prettyJson, Array<NewsVO>::class.java).asList().reversed()
                    recyclerView.adapter = NewsAdapter(newsList)

                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
        return binding.root
    }

    inner class NewsAdapter(
        private val list: List<NewsVO>
    ) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {


        inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(noticia: NewsVO) {
                val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                itemView.findViewById<TextView>(R.id.tituloNoticia).text = noticia.newsTitle
                itemView.findViewById<TextView>(R.id.textoNoticia).text = noticia.news
                itemView.findViewById<TextView>(R.id.horaNoticia).text = "Postado em: " + formatter.format(parser.parse(noticia.insertDate))
                Glide
                    .with(context!!)
                    .load(noticia.image)
                    .into(itemView.findViewById(R.id.imagemNoticia))
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
            val card =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.res_noticia_card, parent, false)
            return NewsHolder(card)
        }

        override fun onBindViewHolder(holder: NewsHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int = list.size

    }

}