package com.example.gestionproduccionesnavegacion.ui.EstadisticasProducciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gestionproduccionesnavegacion.databinding.FragmentEstadisticasProduccionesBinding


class EstadisticasProducciones : Fragment() {

    private var _binding: FragmentEstadisticasProduccionesBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEstadisticasProduccionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            buttonProduccionesTotales.setOnClickListener {
                val action = EstadisticasProduccionesDirections.actionEstadisticasProduccionesToListaTodasProducciones()
                findNavController().navigate(action)
            }

        }

    }


}