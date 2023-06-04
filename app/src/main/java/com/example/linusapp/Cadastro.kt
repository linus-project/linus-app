package com.example.linusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.linusapp.databinding.ActivityCadastroBinding
import com.example.linusapp.utils.Api
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
import java.lang.RuntimeException

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        termoUso()
    }

    fun register(component: View) {
        if (!findViewById<CheckBox>(R.id.checkBox).isChecked) {
            Toast.makeText(
                applicationContext,
                "Por favor aceite os termos de uso",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val service = Api.getUserApi()
            val jsonObjectString = mapUserToJson().toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.addUser(requestBody)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson =
                            gson.toJson(JsonParser.parseString(response.body()?.string()))
                        val activityContent =
                            Intent(applicationContext, PrincipalActivity::class.java)
                        val userResponse: UserVO = gson.fromJson(prettyJson, UserVO::class.java)
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
                        Toast.makeText(
                            applicationContext,
                            "Houve um erro ao cadastrar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    fun mapUserToJson(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("name", findViewById<EditText>(R.id.name_register).text.toString())
        jsonObject.put(
            "username",
            findViewById<EditText>(R.id.username_register).text.toString().lowercase()
        )
        jsonObject.put("email", findViewById<EditText>(R.id.email_register).text.toString())
        jsonObject.put("password", findViewById<EditText>(R.id.password_register).text.toString())
        jsonObject.put("fkLevel", 1)
        return jsonObject
    }

    fun termoUso() {
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.checkBox.setOnClickListener {
            val navegarSegunda = Intent(this, TermoDeUso::class.java)
            startActivity(navegarSegunda)
        }
    }
}