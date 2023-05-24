package com.example.linusapp.ui.distribuicao


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.linusapp.databinding.FragmentDistribuicaoBinding

class DistribuicaoFragment : Fragment() {

    private lateinit var binding: FragmentDistribuicaoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistribuicaoBinding.inflate(inflater, container, false)
        return binding.root
    }
}