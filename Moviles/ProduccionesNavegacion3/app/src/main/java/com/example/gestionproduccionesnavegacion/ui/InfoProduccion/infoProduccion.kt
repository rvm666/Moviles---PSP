package com.example.gestionproduccionesnavegacion.ui.InfoProduccion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gestionproduccionesnavegacion.R
import com.example.gestionproduccionesnavegacion.databinding.FragmentInfoProduccionBinding
import com.example.gestionproduccionesnavegacion.databinding.FragmentPantallaMainEstadisticasBinding

class infoProduccion : Fragment() {
    private var _binding: FragmentInfoProduccionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoProduccionBinding.inflate(inflater, container, false)
        return binding.root
    }


}