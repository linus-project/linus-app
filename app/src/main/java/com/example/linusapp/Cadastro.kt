package com.example.linusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.linusapp.utils.Api
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun register(component: View) {
        val service = Api.getUserApi()
        val jsonObjectString = mapUserToJson().toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.addUser(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(response.body()?.string())
                    )
                    val news = Intent(applicationContext, Noticias::class.java)
                    news.putExtra("response", prettyJson)
                    startActivity(news)
                } else {
                    Toast.makeText(applicationContext, "Houve um erro ao cadastrar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun mapUserToJson(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("name", findViewById<EditText>(R.id.name_register).text.toString())
        jsonObject.put("username", findViewById<EditText>(R.id.username_register).text.toString())
        jsonObject.put("email", findViewById<EditText>(R.id.email_register).text.toString())
        jsonObject.put("password", findViewById<EditText>(R.id.password_register).text.toString())
        jsonObject.put("fkLevel", 1)
        return jsonObject
    }
}