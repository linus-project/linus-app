package com.example.linusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class TelaConteudoTexto : AppCompatActivity() {

    var idContent: Long = 0
    var idUser: Long = 0
    var fkLevel: Long = 0
    var isFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        idContent = intent.getLongExtra("idContent", 0)
        idUser = intent.getLongExtra("idUser", 0)
        fkLevel = intent.getLongExtra("fkLevel", 0)
        super.onCreate(savedInstanceState)
        isFavorite()
        getContent()
        setContentView(R.layout.activity_tela_conteudo_texto)
    }

    fun getContent(){
        val service = Api.getContentApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getContent(idContent)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val content: ContentVO = gson.fromJson(prettyJson, ContentVO::class.java)
                    findViewById<TextView>(R.id.content_title).text = content.contentTitle
                    findViewById<TextView>(R.id.content_text).text = content.content.subSequence(0,1004)
                    findViewById<TextView>(R.id.content_text_two).text = content.content.subSequence(1004,2000)
                    if (isFavorited) {
                        findViewById<ImageView>(R.id.favorite_star).setBackgroundResource(R.mipmap.estrela_preenchida)
                    } else {
                        findViewById<ImageView>(R.id.favorite_star).setBackgroundResource(R.mipmap.estrela_vazia)
                    }
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    findViewById<TextView>(R.id.content_text).text = "Erro => \n idContent = " + idContent +
                            "\n idUser = " + idUser + "\n fkLevel = " + fkLevel
                }
            }
        }
    }

     fun favoriteContent(component: View) {
        val service = Api.getContentApi()
        val jsonObject = JSONObject()
        jsonObject.put("fkUser", idUser)
        jsonObject.put("fkContent", idContent)
        jsonObject.put("contentLevel", fkLevel)
        val jsonObjectString = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.favoriteContent(jsonObjectString)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (isFavorited) {
                        findViewById<ImageView>(R.id.favorite_star).setBackgroundResource(R.mipmap.estrela_vazia)
                        Toast.makeText(applicationContext, "Conteúdo desfavoritado!", Toast.LENGTH_LONG).show()
                        isFavorited = false
                    } else {
                        findViewById<ImageView>(R.id.favorite_star).setBackgroundResource(R.mipmap.estrela_preenchida_fav)
                        Toast.makeText(applicationContext, "Conteúdo favoritado!", Toast.LENGTH_LONG).show()
                        isFavorited = true
                    }
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    findViewById<TextView>(R.id.content_text).text = "Erro => \n idContent = " + idContent +
                            "\n idUser = " + idUser + "\n fkLevel = " + fkLevel
                }
            }
        }
    }

    fun isFavorite() {
        val service = Api.getContentApi()
        val jsonObject = JSONObject()
        jsonObject.put("fkUser", 1)
        jsonObject.put("fkContent", 1)
        jsonObject.put("contentLevel", 1)
        val jsonObjectString = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.isFavorite(idUser, idContent)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    isFavorited = gson.fromJson(prettyJson, Boolean::class.java)
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}