package com.example.linusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.linusapp.utils.Api
import com.example.linusapp.vo.ContentVO
import com.example.linusapp.vo.NewsVO
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

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(component: View) {
        val wrongPassword = findViewById<TextView>(R.id.wrong_password)
        val service = Api.getUserApi()
        val jsonObject = JSONObject()
        jsonObject.put("username", findViewById<TextView>(R.id.username).text.toString().lowercase())
        jsonObject.put("password", findViewById<TextView>(R.id.password).text.toString())
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getLogin(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    val userResponse: UserVO = gson.fromJson(prettyJson, UserVO::class.java)
                    val activityContent = Intent(applicationContext, PrincipalActivity::class.java)
                    activityContent.putExtra("idUser", userResponse.idUser)
                    activityContent.putExtra("name", userResponse.name)
                    activityContent.putExtra("username", userResponse.username)
                    activityContent.putExtra("email", userResponse.email)
                    activityContent.putExtra("password", userResponse.password)
                    activityContent.putExtra("genre", userResponse.genre)
                    activityContent.putExtra("bornDate", userResponse.bornDate)
                    activityContent.putExtra("phoneNumber", userResponse.phoneNumber)
                    activityContent.putExtra("adminKey", userResponse.adminKey)
                    activityContent.putExtra("imageCode", userResponse.imageCode)
                    activityContent.putExtra("fkLevel", userResponse.fkLevel)
                    activityContent.putExtra("isBlocked", userResponse.isBlocked)
                    startActivity(activityContent)
                } else {
                    wrongPassword.visibility = View.VISIBLE
                }
            }
        }
    }
}