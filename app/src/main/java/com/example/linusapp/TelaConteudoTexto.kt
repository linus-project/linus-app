package com.example.linusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.linusapp.utils.Api
import com.example.linusapp.vo.ContentVO
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TelaConteudoTexto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getContent()
        setContentView(R.layout.activity_tela_conteudo_texto)
    }

    fun getContent(){
        val service = Api.getContentApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getContent(intent.getLongExtra("idContent", 0))
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val content: ContentVO = gson.fromJson(prettyJson, ContentVO::class.java)
                    findViewById<TextView>(R.id.content_title).text = content.contentTitle
                    findViewById<TextView>(R.id.content_text).text = content.content.subSequence(0,1004)
                    findViewById<TextView>(R.id.content_text_two).text = content.content.subSequence(1004,2000)
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}