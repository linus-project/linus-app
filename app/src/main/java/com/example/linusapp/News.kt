package com.example.linusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.linusapp.utils.Api
import com.example.linusapp.vo.NewsVO
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class News : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        initializeRV()
    }

    private fun initializeRV() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val service = Api.getNewsApi()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getNews()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val newsList: List<NewsVO> = gson.fromJson(prettyJson, Array<NewsVO>::class.java).asList()
                    recyclerView.adapter = NewsAdapter(newsList)

                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    inner class NewsAdapter(
        private val list: List<NewsVO>
    ) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {


        inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(noticia: NewsVO) {
                itemView.findViewById<TextView>(R.id.tituloNoticia).text = noticia.newsTitle
                itemView.findViewById<TextView>(R.id.textoNoticia).text = noticia.news
                Glide
                    .with(baseContext)
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