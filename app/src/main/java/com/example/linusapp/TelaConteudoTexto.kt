package com.example.linusapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import com.example.linusapp.utils.Api
import com.example.linusapp.vo.ContentVO
import com.example.linusapp.vo.UserVO
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

    private lateinit var userVO: UserVO
    private lateinit var contentVO: ContentVO
    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController
    var isFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        userVO = UserVO(
            intent.getLongExtra("idUser", 0),
            intent.getStringExtra("name").toString(),
            intent.getStringExtra("username").toString(),
            intent.getStringExtra("email").toString(),
            intent.getStringExtra("password").toString(),
            intent.getStringExtra("genre").toString(),
            intent.getStringExtra("bornDate").toString(),
            intent.getStringExtra("phoneNumber").toString(),
            intent.getStringExtra("adminKey").toString(),
            intent.getStringExtra("imageCode").toString(),
            intent.getLongExtra("fkLevel", 0),
            intent.getIntExtra("isBlocked", 0)
        )
        contentVO = ContentVO(
            intent.getLongExtra("idContent", 0),
            "",
            "",
            intent.getLongExtra("idUser", 0),
            intent.getIntExtra("fkLevel", 0),
            0,
            ""
        )
        mediaController = MediaController(this)
        isFavorite()
        getContent()
        setContentView(R.layout.activity_tela_conteudo_texto)
        super.onCreate(savedInstanceState)
    }

    fun getContent(){
        val service = Api.getContentApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getContent(contentVO.idContent)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    contentVO = gson.fromJson(prettyJson, ContentVO::class.java)
                    findViewById<TextView>(R.id.content_title).text = contentVO.contentTitle
                    findViewById<TextView>(R.id.content_text).text = contentVO.content.subSequence(0,1004)
                    findViewById<TextView>(R.id.content_text_two).text = contentVO.content.subSequence(1004,2000)
                    if (isFavorited) {
                        findViewById<ImageView>(R.id.favorite_star).setBackgroundResource(R.drawable.star_filled )
                    } else {
                        findViewById<ImageView>(R.id.favorite_star).setBackgroundResource(R.drawable.star)
                    }
                    saveHistoryContent()
                    videoView = findViewById(R.id.videoView);
                    val uri: Uri = Uri.parse(contentVO.videoPath)
                    videoView.setVideoURI(uri)
                    mediaController.setAnchorView(videoView)
                    mediaController.setMediaPlayer(videoView)
                    videoView.setMediaController(mediaController)
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    findViewById<TextView>(R.id.content_text).text = "Erro => \n idContent = " + contentVO.idContent +
                            "\n idUser = " + userVO.idUser + "\n fkLevel = " + contentVO.fkLevel
                }
            }
        }
    }

     fun favoriteContent(component: View) {
        val service = Api.getContentApi()
        val jsonObject = JSONObject()
        jsonObject.put("fkUser", userVO.idUser)
        jsonObject.put("fkContent", contentVO.idContent)
        jsonObject.put("contentLevel", contentVO.fkLevel)
        val jsonObjectString = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.favoriteContent(jsonObjectString)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (isFavorited) {
                        findViewById<ImageView>(R.id.favorite_star).setBackgroundResource(R.drawable.star)
                        isFavorited = false
                    } else {
                        findViewById<ImageView>(R.id.favorite_star).setBackgroundResource(R.drawable.star_filled)
                        isFavorited = true
                    }
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    findViewById<TextView>(R.id.content_text).text = "Erro => \n idContent = " + contentVO.idContent +
                            "\n idUser = " + userVO.idUser + "\n fkLevel = " + contentVO.fkLevel
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
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.isFavorite(userVO.idUser, contentVO.idContent)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    isFavorited = gson.fromJson(prettyJson, Boolean::class.java)
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun saveHistoryContent() {
        val service = Api.getContentApi()
        val jsonObject = JSONObject()
        jsonObject.put("fkUser", userVO.idUser)
        jsonObject.put("fkContent", contentVO.idContent)
        jsonObject.put("contentLevel", contentVO.fkLevel)
        CoroutineScope(Dispatchers.IO).launch {
            service.saveHistoryContent(jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull()))
            withContext(Dispatchers.Main) {}
        }
    }
}