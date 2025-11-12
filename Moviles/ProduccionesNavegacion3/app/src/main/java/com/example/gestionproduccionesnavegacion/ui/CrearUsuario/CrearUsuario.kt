package com.example.gestionproduccionesnavegacion.ui.CrearUsuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestionproduccionesnavegacion.databinding.FragmentCrearUsuarioBinding

class CrearUsuario : Fragment() {

    private var _binding: FragmentCrearUsuarioBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrearUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

}