package com.example.gestionproduccionesnavegacion.ui.AniadirProduccion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gestionproduccionesnavegacion.databinding.FragmentAniadirProduccionBinding

class AniadirProduccion : Fragment() {

    private var _binding: FragmentAniadirProduccionBinding? = null
    private val binding get() = _binding!!

    private val ViewModel: AniadirProduccionViewModel by viewModels ()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAniadirProduccionBinding.inflate(inflater, container, false)
        return binding.root
    }


}