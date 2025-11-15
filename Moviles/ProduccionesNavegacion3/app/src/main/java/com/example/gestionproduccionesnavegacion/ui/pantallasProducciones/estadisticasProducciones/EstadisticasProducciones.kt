package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.estadisticasProducciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gestionproduccionesnavegacion.databinding.FragmentEstadisticasProduccionesBinding
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EstadisticasProducciones : Fragment() {

    private var _binding: FragmentEstadisticasProduccionesBinding? = null
    private val binding get() = _binding!!

    private val estadisticasProduccionesViewModel: EstadisticasProduccionesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEstadisticasProduccionesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventos()
        observacion()
    }


    private fun eventos(){
        with(binding){
            buttonProduccionesTotales.setOnClickListener {
                val action = EstadisticasProduccionesDirections.Companion.actionEstadisticasProduccionesToListaTodasProducciones()
                findNavController().navigate(action)
            }

            estadisticasProduccionesViewModel.getTotalProducciones()
            estadisticasProduccionesViewModel.getProduccionmasVista()
            estadisticasProduccionesViewModel.getGeneroPopular()
        }
    }


    private fun observacion(){
        estadisticasProduccionesViewModel.state.observe(viewLifecycleOwner){ state ->
            binding.produccionesTotales.text = state.numeroProducciones.toString()
            binding.produccionMasVistas.text = state.masVistada
            binding.generoMasVisto.text = state.generoMasVisto
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}