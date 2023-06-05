package com.example.linusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.linusapp.ui.content.FavoriteContentFragment
import java.util.Timer
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        goToOnboarding()
    }

    fun goToOnboarding() {
        val onboarding = Intent(applicationContext, Onboarding::class.java)
        Timer().schedule(2900){
            startActivity(onboarding)
        }
    }
}