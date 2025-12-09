package com.example.gestionproduccionesnavegacion.ui.PantallaMainEstadisticas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestionproduccionesnavegacion.databinding.FragmentPantallaMainEstadisticasBinding

class PantallaMainEstadisticas : Fragment() {

    private var _binding: FragmentPantallaMainEstadisticasBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPantallaMainEstadisticasBinding.inflate(inflater, container, false)
        return binding.root
    }


}