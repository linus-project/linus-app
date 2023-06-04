package com.example.linusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.linusapp.databinding.ActivityTermoDeUsoBinding

class TermoDeUso : AppCompatActivity() {

    private lateinit var binding: ActivityTermoDeUsoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termo_de_uso)

        termoAceito()
    }

    fun termoAceito(){
        binding = ActivityTermoDeUsoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botaoAceite.setOnClickListener{
            val navegarSegunda = Intent(this, Cadastro::class.java)
            startActivity(navegarSegunda)
        }
    }
}