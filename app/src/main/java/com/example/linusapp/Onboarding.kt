package com.example.linusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Onboarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }

    fun redirectToLogin(component: View) {
        startActivity(Intent(applicationContext, Login::class.java))
    }

    fun redirectToRegister(component: View) {
        startActivity(Intent(applicationContext, Cadastro::class.java))
    }
}